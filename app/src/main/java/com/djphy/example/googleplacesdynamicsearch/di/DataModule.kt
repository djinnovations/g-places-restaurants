package com.djphy.example.googleplacesdynamicsearch.di

import com.djphy.example.googleplacesdynamicsearch.repo.PlacesDataRepo
import com.djphy.example.googleplacesdynamicsearch.repo.PlacesDataRepoI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Singleton
    @Provides
    fun provideHomeRepositoryI(): PlacesDataRepoI {
        return PlacesDataRepo()
    }
}