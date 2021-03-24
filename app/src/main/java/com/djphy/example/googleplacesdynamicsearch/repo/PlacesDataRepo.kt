package com.djphy.example.googleplacesdynamicsearch.repo

import com.djphy.example.googleplacesdynamicsearch.GPlacesRestaurantApp
import com.djphy.example.googleplacesdynamicsearch.remote.GenericNetworkResponseSingle
import com.djphy.example.googleplacesdynamicsearch.remote.NetworkResponse
import com.djphy.example.googleplacesdynamicsearch.remote.network.models.RestaurantList
import com.djphy.example.googleplacesdynamicsearch.remote.network.service.PlaceDataApiService
import io.reactivex.Single
import javax.inject.Inject

class PlacesDataRepo: PlacesDataRepoI {

    @Inject
    lateinit var apiService: PlaceDataApiService

    init {
        GPlacesRestaurantApp.appComponent().inject(this)
    }

    override fun getLocaleRestaurantList(latLng: String): Single<NetworkResponse<RestaurantList>> {
        val apiCall = apiService.getLocaleBasedRestaurants(latLng)
        return GenericNetworkResponseSingle(apiCall, RestaurantList::class)
    }

    override fun getLocaleKeywordRestaurantList(latLng: String, searchKey: String):
            Single<NetworkResponse<RestaurantList>> {
        val apiCall = apiService.getKeywordBasedRestaurants(latLng, searchKey)
        return GenericNetworkResponseSingle(apiCall, RestaurantList::class)
    }
}