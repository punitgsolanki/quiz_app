package com.example.quizdemo.data.model

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoadingShow: Boolean? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Loading<T>(isLoadingShow: Boolean) : Resource<T>(isLoadingShow = isLoadingShow)
    class Error<T>(message: String) : Resource<T>(message = message)
    class NoInternetError<T>(message: String) : Resource<T>(message = message)
}