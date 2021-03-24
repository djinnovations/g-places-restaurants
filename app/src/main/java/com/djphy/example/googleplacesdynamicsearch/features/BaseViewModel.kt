package com.djphy.example.googleplacesdynamicsearch.features

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.djphy.example.googleplacesdynamicsearch.TAG
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {

    protected val mBaseDisposables: CompositeDisposable = CompositeDisposable()

    abstract val mBaseStateObservable: MutableLiveData<T>


    protected open fun publishState(state: T){
        mBaseStateObservable.postValue(state)
    }

    override fun onCleared() {
        Log.i(TAG, "View Cleared")
        mBaseDisposables.clear()
        super.onCleared()
    }
}