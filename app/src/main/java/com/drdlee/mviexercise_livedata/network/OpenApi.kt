package com.drdlee.mviexercise_livedata.network

import androidx.lifecycle.LiveData
import com.drdlee.mviexercise_livedata.models.Blog
import com.drdlee.mviexercise_livedata.models.User
import com.drdlee.mviexercise_livedata.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenApi {

    @GET("placeholder/user/{userId}")
    fun fetchUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun fetchBlog(): LiveData<GenericApiResponse<List<Blog>>>
}