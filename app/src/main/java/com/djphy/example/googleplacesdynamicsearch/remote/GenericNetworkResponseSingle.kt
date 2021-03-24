package com.djphy.example.googleplacesdynamicsearch.remote

import android.util.Log
import io.reactivex.Single
import io.reactivex.SingleObserver
import retrofit2.Call
import kotlin.reflect.KClass

class GenericNetworkResponseSingle<T : Any>(
    private val call : Call<T>, val responseOnError : T) : Single<NetworkResponse<T>>() {

    constructor(call : Call<T>, entityClass : KClass<T>) : this(call, entityClass.java.newInstance())

    private val TAG: String = GenericNetworkResponseSingle::class.java.getSimpleName()

    override fun subscribeActual(observer: SingleObserver<in NetworkResponse<T>>) {
        GenericReqExecutor(
            call
        ).executeCallRequest(object :
            ExecutionListener<T> {
            override fun onSuccess(response: T) {
                observer.onSuccess(
                    NetworkResponse(
                        success = true,
                        response = response
                    )
                )
            }

            override fun onApiError(error: NetworkError) {
                try {
                    observer.onSuccess(
                        NetworkResponse(
                            success = false,
                            response = responseOnError,
                            networkError = error
                        )
                    )
                } catch (ex : Exception) {
                    Log.e(TAG, ex.printStackTrace().toString())
                    observer.onError(Exception(error.message))
                }
            }

            override fun onFailure(error: Throwable) {
                observer.onError(error)
            }
        })
    }
}