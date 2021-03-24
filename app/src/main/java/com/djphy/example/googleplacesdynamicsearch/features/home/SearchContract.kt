package com.djphy.example.googleplacesdynamicsearch.features.home

interface SearchContract {
    fun provideSearch(query: String)
    fun onSearchClosed()
}