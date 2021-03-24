package com.djphy.example.googleplacesdynamicsearch.remote

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenericReqExecutor<T>() {

    private lateinit var mCallRequest: Call<T>

    constructor(callRequest: Call<T>) : this() {
        mCallRequest = callRequest
    }

    fun executeCallRequest(listener: ExecutionListener<T>) {
        try {
            if (!mCallRequest.isExecuted) {
                mCallRequest.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>?, response: Response<T>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                it.body()?.let { body ->

                                    listener.onSuccess(body)
                                } ?: run {
                                    listener.onSuccess(Unit as T)
                                }
                            } else {
                                response.errorBody()?.let {
                                    val errorBodyString = it.string()
                                    val error =
                                        GsonBuilder().create()
                                            .fromJson(errorBodyString, NetworkError::class.java)
                                    error?.let {
                                        if (error.message.isNotEmpty()) {
                                            listener.onApiError(error)
                                        }
                                    }?: kotlin.run{
                                        listener.onApiError(NetworkError(message = "no network"))
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<T>, throwable: Throwable) {
                        listener.onFailure(throwable)
                    }
                })
            }
        } catch (ex: Exception) {
            listener.onFailure(ex)
        }

    }

}