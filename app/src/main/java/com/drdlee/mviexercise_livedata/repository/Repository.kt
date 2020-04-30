package com.drdlee.mviexercise_livedata.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.drdlee.mviexercise_livedata.network.RetrofitBuilder
import com.drdlee.mviexercise_livedata.ui.main.state.MainViewState
import com.drdlee.mviexercise_livedata.util.ApiEmptyResponse
import com.drdlee.mviexercise_livedata.util.ApiErrorResponse
import com.drdlee.mviexercise_livedata.util.ApiSuccessResponse

object Repository {

    /**
     * Watch API call,
     * if value returned from API, which will be GenericApiResponse<User>
     *
     * then create new object with type LiveData<MainViewState>
     * and return value based on which API response
     */
    fun getUser(userId: String): LiveData<MainViewState> {
        return Transformations.switchMap(RetrofitBuilder.openApi.fetchUser(userId)) { apiResponse ->
            object : LiveData<MainViewState>() {
                override fun onActive() {
                    super.onActive()
                    value = when (apiResponse) {
                        is ApiSuccessResponse -> MainViewState(user = apiResponse.body)
                        is ApiEmptyResponse -> MainViewState()
                        is ApiErrorResponse -> MainViewState()
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
    fun getBlogList(): LiveData<MainViewState> {
        return Transformations.switchMap(RetrofitBuilder.openApi.fetchBlog()) { apiResponse ->
            object : LiveData<MainViewState>() {
                override fun onActive() {
                    super.onActive()
                    value = when (apiResponse) {
                        is ApiSuccessResponse -> MainViewState(blogPost = apiResponse.body)
                        is ApiEmptyResponse -> MainViewState()
                        is ApiErrorResponse -> MainViewState()
                    }
                }
            }
        }
    }
}