package com.djphy.example.googleplacesdynamicsearch

import android.app.Application
import com.djphy.example.googleplacesdynamicsearch.di.AppComponent
import com.djphy.example.googleplacesdynamicsearch.di.DaggerAppComponent
import com.djphy.example.googleplacesdynamicsearch.di.DaggerDataComponent
import com.djphy.example.googleplacesdynamicsearch.di.DataComponent

class GPlacesRestaurantApp : Application() {

    private val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    private val dataComponent : DataComponent by lazy {
        DaggerDataComponent.create()
    }

    init {
        instance = this
    }

    companion object{
        private lateinit var instance: GPlacesRestaurantApp

        fun getApp() : GPlacesRestaurantApp {
            return instance
        }

        fun appComponent(): AppComponent {
            return instance.appComponent
        }

        fun dataComponent(): DataComponent {
            return instance.dataComponent
        }
    }
}