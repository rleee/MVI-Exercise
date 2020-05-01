package com.drdlee.mviexercise_livedata.util

data class DataState<T>(
    var data: T? = null,
    var message: String? = null,
    var loading: Boolean = false
) {
    companion object {
        fun <T> onError(message: String): DataState<T> {
            return DataState(
                data = null,
                message = message,
                loading = false
            )
        }

        fun <T> onLoading(isLoading: Boolean): DataState<T> {
            return DataState(
                data = null,
                message = null,
                loading = isLoading
            )
        }

        fun <T> onData(message: String? = null, data: T? = null): DataState<T> {
            return DataState(
                data = data,
                message = message,
                loading = false
            )
        }
    }
}