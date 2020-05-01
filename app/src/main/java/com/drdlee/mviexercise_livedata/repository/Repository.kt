package com.drdlee.mviexercise_livedata.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.drdlee.mviexercise_livedata.network.RetrofitBuilder
import com.drdlee.mviexercise_livedata.ui.main.state.MainViewState
import com.drdlee.mviexercise_livedata.util.ApiEmptyResponse
import com.drdlee.mviexercise_livedata.util.ApiErrorResponse
import com.drdlee.mviexercise_livedata.util.ApiSuccessResponse
import com.drdlee.mviexercise_livedata.util.DataState

object Repository {

    /**
     * Watch API call,
     * if value returned from API, which will be GenericApiResponse<User>
     *
     * then create new object with type LiveData<MainViewState>
     * and return value based on which API response
     */
    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(RetrofitBuilder.openApi.fetchUser(userId)) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    value = when (apiResponse) {
                        is ApiSuccessResponse -> DataState.onData(data = MainViewState(user = apiResponse.body))
                        is ApiEmptyResponse -> DataState.onError(message = "HTTP 204. Empty Response")
                        is ApiErrorResponse -> DataState.onError(message = apiResponse.errorMessage)
                    }
                }
            }
        }
    }

    /**
     * Watch API call,
     * if value returned from API, which will be GenericApiResponse<User>
     *
     * then create new object with type LiveData<MainViewState>
     * and return value based on which API response
     */
    fun getBlogList(): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(RetrofitBuilder.openApi.fetchBlog()) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    value = when (apiResponse) {
                        is ApiSuccessResponse -> DataState.onData(data = MainViewState(blogPost = apiResponse.body))
                        is ApiEmptyResponse -> DataState.onError(message = "HTTP 204. Empty Response")
                        is ApiErrorResponse -> DataState.onError(message = apiResponse.errorMessage)
                    }
                }
            }
        }
    }
}