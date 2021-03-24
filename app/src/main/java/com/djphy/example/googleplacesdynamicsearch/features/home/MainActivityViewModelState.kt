package com.djphy.example.googleplacesdynamicsearch.features.home

import com.djphy.example.googleplacesdynamicsearch.remote.network.models.RestaurantList


data class MainActivityViewModelState (
    var loading: Boolean = false,
    var failure: Boolean = false,
    var success: Boolean = false,
    var noLocation: Boolean = false,
    var message: String = "",
    var data: RestaurantList? = null
)