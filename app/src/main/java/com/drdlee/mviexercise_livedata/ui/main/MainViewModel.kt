package com.drdlee.mviexercise_livedata.ui.main

import androidx.lifecycle.*
import com.drdlee.mviexercise_livedata.models.Blog
import com.drdlee.mviexercise_livedata.models.User
import com.drdlee.mviexercise_livedata.repository.Repository
import com.drdlee.mviexercise_livedata.ui.main.state.MainStateEvent
import com.drdlee.mviexercise_livedata.ui.main.state.MainViewState

class MainViewModel : ViewModel() {

    private val _eventState = MutableLiveData<MainStateEvent>()
    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> =
        Transformations.switchMap(_eventState) { stateEvent ->
            when (stateEvent) {
                is MainStateEvent.GetUserEvent -> Repository.getUser(stateEvent.userId)
                is MainStateEvent.GetBlogPostEvent -> Repository.getBlogList()
                is MainStateEvent.None -> object : LiveData<MainViewState>() {}
            }
        }

    /**
     * Set EventState when option menu is pressed
     */
    fun setEventState(eventState: MainStateEvent) {
        _eventState.value = eventState
    }

    /**
     * Get current viewState or create new viewState
     */
    private fun getCurrentStateOrCreateNew(): MainViewState {
        return _viewState.value ?: MainViewState()
    }

    /**
     * Set viewState from dataState
     */
    fun setUser(user: User) {
        val vState = getCurrentStateOrCreateNew()
        vState.user = user
        _viewState.value = vState
    }

    /**
     * Set viewState from dataState
     */
    fun setBlogList(blogList: List<Blog>) {
        val vState = getCurrentStateOrCreateNew()
        vState.blogPost = blogList
        _viewState.value = vState
    }
}