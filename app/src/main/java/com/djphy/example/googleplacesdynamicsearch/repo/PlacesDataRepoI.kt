package com.djphy.example.googleplacesdynamicsearch.repo

import com.djphy.example.googleplacesdynamicsearch.remote.NetworkResponse
import com.djphy.example.googleplacesdynamicsearch.remote.network.models.RestaurantList
import io.reactivex.Single

interface PlacesDataRepoI {

    fun getLocaleRestaurantList(latLng: String) : Single<NetworkResponse<RestaurantList>>

    fun getLocaleKeywordRestaurantList(latLng: String, searchKey: String) : Single<NetworkResponse<RestaurantList>>


}