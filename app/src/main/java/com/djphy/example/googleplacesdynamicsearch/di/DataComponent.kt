package com.djphy.example.googleplacesdynamicsearch.di

import com.djphy.example.googleplacesdynamicsearch.features.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class
    ]
)
interface DataComponent {
    fun inject(mainActivity: MainActivity)
}