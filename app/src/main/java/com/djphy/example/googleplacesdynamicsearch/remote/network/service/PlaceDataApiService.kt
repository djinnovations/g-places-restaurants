package com.djphy.example.googleplacesdynamicsearch.remote.network.service

import com.djphy.example.googleplacesdynamicsearch.remote.network.models.RestaurantList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceDataApiService {

    @GET("maps/api/place/nearbysearch/json")
    fun getLocaleBasedRestaurants(
        @Query("location") latLngString : String
    ): Call<RestaurantList>

    @GET("maps/api/place/nearbysearch/json")
    fun getKeywordBasedRestaurants(
        @Query("location") latLngString : String,
        @Query("keyword") searchKey : String
    ): Call<RestaurantList>
}