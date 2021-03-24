package com.djphy.example.googleplacesdynamicsearch.remote.network.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Keep
@Parcelize
data class RestaurantList(
    @SerializedName("results")
    @Expose
    var restaurantList : List<RestaurantItem> = emptyList()

): Parcelable {
    @Keep
    @Parcelize
    data class RestaurantItem(
        @SerializedName("name")
        @Expose
        var name: String = "",
        @SerializedName("icon")
        @Expose
        var imgUrl: String? = null,
        @SerializedName("rating")
        @Expose
        var rating: String = "",
        @SerializedName("photos")
        @Expose
        var photosList : List<Photo>? = null
    ) : Parcelable

    @Keep
    @Parcelize
    data class Photo(
        @SerializedName("photo_reference")
        @Expose
        var p_ref: String? = null
    ): Parcelable
}