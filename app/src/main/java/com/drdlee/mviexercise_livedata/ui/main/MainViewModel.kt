package com.drdlee.mviexercise_livedata.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.drdlee.mviexercise_livedata.models.User
import com.drdlee.mviexercise_livedata.network.RetrofitBuilder
import com.drdlee.mviexercise_livedata.util.ApiSuccessResponse

class MainViewModel : ViewModel() {

    private val _response = MediatorLiveData<User>()
    val response: LiveData<User>
        get() = _response

    init {
        val tempResponse = RetrofitBuilder.openApi.fetchUser("1")
        _response.addSource(tempResponse) { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    _response.value = apiResponse.body
                }
            }
        }
    }
}