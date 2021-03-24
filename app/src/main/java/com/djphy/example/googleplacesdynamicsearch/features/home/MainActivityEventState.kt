package com.djphy.example.googleplacesdynamicsearch.features.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class MainActivityEventState: Parcelable {

    @Parcelize
    object HomeLaunchedState : MainActivityEventState(), Parcelable

    @Parcelize
    object NoLocationAvailableState : MainActivityEventState(), Parcelable

    @Parcelize
    object InitPlacesState : MainActivityEventState(), Parcelable

    @Parcelize
    object TryWithSampleState : MainActivityEventState(), Parcelable

    @Parcelize
    data class SearchRestaurantState(val searchKey: String) : MainActivityEventState(), Parcelable

}