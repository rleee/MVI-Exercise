package com.drdlee.mviexercise_livedata.ui.main

import com.drdlee.mviexercise_livedata.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}