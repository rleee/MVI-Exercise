package com.drdlee.mviexercise_livedata.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.drdlee.mviexercise_livedata.util.*
import kotlinx.coroutines.*

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
            delay(1_000L)
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