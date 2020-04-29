package com.drdlee.mviexercise_livedata.network

import com.drdlee.mviexercise_livedata.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://open-api.xyz/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }

    val openApi: OpenApi by lazy {
        retrofitBuilder
            .build()
            .create(OpenApi::class.java)
    }
}