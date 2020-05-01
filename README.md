# MVI-Exercise
MVI example from codingwithmitch

Most of the stuff are using generics, and the data flow will be:
1. **Retrofit** api response as:
   - `GenericApiResponse<T>` will contains:
     - `ApiSuccessResponse<T>(val body: T)`
     - `ApiEmptyResponse<T>`
     - `ApiErrorResponse<T>(val errorMessage: String)`
     <br>
     
     > 🐣 `<T>` here would be response from Api which is `User` or `List<Blog>`
     
2. **Repository** <br>
repository will use `NetworkBoundResource` to pass response to `DataState<MainViewState>`
   - `NetworkBoundResource<T, ViewState>` <br> 
     - `abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>` <br>
     - `abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)`
     <br>
     
     > 🐹 have a property `MediatorLiveData<DataState<ViewState>>` as temporary `DataState<ViewState>` <br>
     we `createCall()` then handle the response send back from api <br>
     by default `ApiEmptyResponse` & `ApiErrorResponse` will handle internally in `NetworkBoundResource` and keep it in MediatorLiveData <br>
     `ApiSuccessResponse` will be handled by user defined callback in repository
     
   - `DataState<MainViewState>` will be returned from `NetworkBoundResource`
   
3. **ViewModel** <br>
will have 3 properties that will handle user request and give feedback
   - **eventState:** set user input state
   - **dataState:** react to eventState / fetch data from Repository
   - **viewState:** temporarily keep data from dataState / provide data to Views
   


