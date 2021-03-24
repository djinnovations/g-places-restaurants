package com.djphy.example.googleplacesdynamicsearch.di

import android.content.Context
import androidx.annotation.NonNull
import com.djphy.example.googleplacesdynamicsearch.remote.network.interceptor.KeyTypeReqInterceptor
import com.djphy.example.googleplacesdynamicsearch.remote.network.service.PlaceDataApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
        const val GPLACES_KEY = "AIzaSyCOIv5k0oQBSZeV-OjbD3DEvffWbL4O4KY"
    }

    @Provides
    @Singleton
    fun provideHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(300, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(KeyTypeReqInterceptor())
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApiService(@NonNull retrofit: Retrofit): PlaceDataApiService {
        return retrofit.create(PlaceDataApiService::class.java)
    }
}