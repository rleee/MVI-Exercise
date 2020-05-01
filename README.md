# MVI-Exercise
MVI example from codingwithmitch

Most of the stuff are using generics, and the data flow will be:
1. [**Retrofit api**](retrofit-api) response as:
   - `GenericApiResponse<T>` will contains:
     - `ApiSuccessResponse<T>(val body: T)`
     - `ApiEmptyResponse<T>`
     - `ApiErrorResponse<T>(val errorMessage: String)`
     <br>
     
     > 🐣 `<T>` here would be response from Api which is `User` or `List<Blog>`
     
2. [**Repository**](#repository) <br>
repository will use [`NetworkBoundResource`](#NetworkBoundResource) to pass response to `DataState<MainViewState>`
   - `NetworkBoundResource<T, ViewState>` <br> 
     - `abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>` <br>
     - `abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)`
     <br>
     
     > 🐹 have a property `MediatorLiveData<DataState<ViewState>>` as temporary `DataState<ViewState>` <br>
     we `createCall()` then handle the response send back from api <br>
     by default `ApiEmptyResponse` & `ApiErrorResponse` will handle internally in `NetworkBoundResource` and keep it in MediatorLiveData <br>
     `ApiSuccessResponse` will be handled by user defined callback in repository
     
   - `DataState<MainViewState>` will be returned from `NetworkBoundResource`
   
3. [**ViewModel**](#ViewModel) <br>
will have 3 properties that will handle user request and give feedback
   - **eventState:** set user input state
   - **dataState:** react to eventState / fetch data from Repository
   - **viewState:** temporarily keep data from dataState / provide data to Views
   
---

##### Retrofit Api
```kotlin
interface OpenApi {

    @GET("placeholder/user/{userId}")
    fun fetchUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun fetchBlog(): LiveData<GenericApiResponse<List<Blog>>>
}
```

##### Repository
```kotlin
object Repository {

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.openApi.fetchUser(userId)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.onData(data = MainViewState(user = response.body))
            }
        }.asLiveData()
    }
    
    ...
}
```

##### NetworkBoundResource
```kotlin
/**
 * ResponseObject: is object returned from retrofit (User / ListBlog)
 * ViewState     : is state we will use to display the data
 */
abstract class NetworkBoundResource<ResponseObject, ViewState> {

    protected val result = MediatorLiveData<DataState<ViewState>>()

    init {
        // set onLoading DataState
        result.value = DataState.onLoading(true)

        GlobalScope.launch(Dispatchers.IO) {
            delay(1_000L) // delay only to give 1 sec loading state 
            withContext(Dispatchers.Main) {
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }
        }
    }

    /**
     * Set onError DataState -> handleErrorResponse
     * Set onData DataState -> handleApiSuccessResponse
     */
    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> handleApiSuccessResponse(response)
            is ApiEmptyResponse -> handleErrorResponse(message = "HTTP 204. Empty Response")
            is ApiErrorResponse -> handleErrorResponse(message = response.errorMessage)
        }
    }

    private fun handleErrorResponse(message: String) {
        result.value = DataState.onError(message = message)
    }

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>
    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    /**
     * Just to convert this MediatorLiveData to LiveData
     */
    fun asLiveData() = result as LiveData<DataState<ViewState>>
}
```

##### ViewModel
```kotlin
class MainViewModel : ViewModel() {

    private val _eventState = MutableLiveData<MainStateEvent>()
    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> =
        Transformations.switchMap(_eventState) { stateEvent ->
            when (stateEvent) {
                is MainStateEvent.GetUserEvent -> Repository.getUser(stateEvent.userId)
                is MainStateEvent.GetBlogPostEvent -> Repository.getBlogList()
                is MainStateEvent.None -> object : LiveData<DataState<MainViewState>>() {}
            }
        }
        
        ...
}
```



