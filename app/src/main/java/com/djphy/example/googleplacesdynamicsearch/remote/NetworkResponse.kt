package com.djphy.example.googleplacesdynamicsearch.remote

import androidx.annotation.Keep
import okhttp3.Headers

@Keep
data class NetworkResponse<T>(
    val success: Boolean,
    val response: T,
    val responseHeader: Headers? = null,
    val networkError: NetworkError = NetworkError()
)

@Keep
data class NetworkError(
    val name: String = "",
    val message: String = "",
    val code: String = "",
    val status: Int = 0
)