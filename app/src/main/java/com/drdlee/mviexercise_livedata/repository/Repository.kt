package com.drdlee.mviexercise_livedata.repository

import androidx.lifecycle.LiveData
import com.drdlee.mviexercise_livedata.models.Blog
import com.drdlee.mviexercise_livedata.models.User
import com.drdlee.mviexercise_livedata.network.RetrofitBuilder
import com.drdlee.mviexercise_livedata.ui.main.state.MainViewState
import com.drdlee.mviexercise_livedata.util.ApiSuccessResponse
import com.drdlee.mviexercise_livedata.util.DataState
import com.drdlee.mviexercise_livedata.util.GenericApiResponse

object Repository {

    /**
     * Watch API call,
     * if value returned from API, which will be GenericApiResponse<User>
     *
     * then create new object with type LiveData<MainViewState>
     * and return value based on which API response
     */
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

    /**
     * Watch API call,
     * if value returned from API, which will be GenericApiResponse<User>
     *
     * then create new object with type LiveData<MainViewState>
     * and return value based on which API response
     */
    fun getBlogList(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<Blog>, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<List<Blog>>> {
                return RetrofitBuilder.openApi.fetchBlog()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Blog>>) {
                result.value = DataState.onData(data = MainViewState(blogPost = response.body))
            }
        }.asLiveData()
    }
}