package com.drdlee.mviexercise_livedata.ui.main.state

import com.drdlee.mviexercise_livedata.models.Blog
import com.drdlee.mviexercise_livedata.models.User

data class MainViewState(
    var blogPost: List<Blog>? = null,
    var user: User? = null
)