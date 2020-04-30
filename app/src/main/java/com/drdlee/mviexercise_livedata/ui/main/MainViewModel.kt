package com.drdlee.mviexercise_livedata.ui.main

import androidx.lifecycle.*
import com.drdlee.mviexercise_livedata.repository.Repository
import com.drdlee.mviexercise_livedata.ui.main.state.MainStateEvent
import com.drdlee.mviexercise_livedata.ui.main.state.MainViewState

class MainViewModel : ViewModel() {

    private val _eventState = MutableLiveData<MainStateEvent>()

    val dataState: LiveData<MainViewState> =
        Transformations.switchMap(_eventState) { stateEvent ->
            when (stateEvent) {
                is MainStateEvent.GetUserEvent -> Repository.getUser(stateEvent.userId)
                is MainStateEvent.GetBlogPostEvent -> Repository.getBlogList()
                is MainStateEvent.None -> object : LiveData<MainViewState>() {}
            }
        }

    fun setEventState(eventState: MainStateEvent) {
        _eventState.value = eventState
    }
}