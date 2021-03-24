package com.djphy.example.googleplacesdynamicsearch.remote.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class KeyTypeReqInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("key", "AIzaSyCOIv5k0oQBSZeV-OjbD3DEvffWbL4O4KY")
            .addQueryParameter("radius", "2500")
            .addQueryParameter("type", "restaurant")
            .build()
        request = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

}