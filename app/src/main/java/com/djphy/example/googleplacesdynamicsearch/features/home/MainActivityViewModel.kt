package com.djphy.example.googleplacesdynamicsearch.features.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.djphy.example.googleplacesdynamicsearch.TAG
import com.djphy.example.googleplacesdynamicsearch.features.BaseViewModel
import com.djphy.example.googleplacesdynamicsearch.repo.PlacesDataRepoI

class MainActivityViewModel(
    private val homeRepoI: PlacesDataRepoI,
    app: Application
) : BaseViewModel<MainActivityViewModelState>(app) {

    init {
        Log.i(TAG, "View Model creation")
    }

    override val mBaseStateObservable: MutableLiveData<MainActivityViewModelState> by lazy {
        MutableLiveData<MainActivityViewModelState>()
    }

    private var mBaseState =
        MainActivityViewModelState()
        set(value) {
            field = value
            publishState(value)
        }

    fun updateNoLocation(){
        mBaseState = MainActivityViewModelState(noLocation = true,
            message = "12.913907,77.638119 - bda complex, HSR layout")
    }

    fun getRestaurantsByLatLng(latLng: String) {
        mBaseState = MainActivityViewModelState(loading = true)
        homeRepoI.getLocaleRestaurantList(latLng)
            .subscribe({
                mBaseState = if (it.success) {
                    mBaseState.copy(loading = false, success = true, data = it.response)
                } else {
                    mBaseState.copy(loading = false, failure = true, message = it.networkError.message)
                }
            }, {
                Log.e(TAG, "${it.stackTrace}")
                mBaseState = mBaseState.copy(loading = false, failure = true, message = it.localizedMessage!!)
            }).apply {
                mBaseDisposables.add(this)
            }
    }

    fun getRestaurantsBySearch(latLng: String, searchKey: String) {
        mBaseState = mBaseState.copy(loading = true)
        homeRepoI.getLocaleKeywordRestaurantList(latLng, searchKey)
            .subscribe({
                mBaseState = if (it.success) {
                    mBaseState.copy(loading = false, success = true, data = it.response)
                } else {
                    mBaseState.copy(loading = false, failure = true, message = it.networkError.message)
                }
            }, {
                Log.e(TAG, "${it.stackTrace}")
                mBaseState = mBaseState.copy(loading = false, failure = true, message = it.localizedMessage!!)
            }).apply {
                mBaseDisposables.add(this)
            }
    }

}