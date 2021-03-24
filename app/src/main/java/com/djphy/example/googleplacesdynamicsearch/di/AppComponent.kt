package com.djphy.example.googleplacesdynamicsearch.di

import android.content.Context
import com.djphy.example.googleplacesdynamicsearch.repo.PlacesDataRepo
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class]
)
interface AppComponent {

    fun inject(moviesRepo: PlacesDataRepo)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context) : Builder

        fun build() : AppComponent
    }
}