package com.djphy.example.googleplacesdynamicsearch.features.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.djphy.example.googleplacesdynamicsearch.GPlacesRestaurantApp
import com.djphy.example.googleplacesdynamicsearch.R
import com.djphy.example.googleplacesdynamicsearch.createFactory
import com.djphy.example.googleplacesdynamicsearch.databinding.ActivityMainBinding
import com.djphy.example.googleplacesdynamicsearch.features.BaseSearchActivity
import com.djphy.example.googleplacesdynamicsearch.repo.PlacesDataRepoI
import com.djphy.example.googleplacesdynamicsearch.toast
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseSearchActivity() {

    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mAdapter: RestaurantAdapter
    private lateinit var mDataBinding: ActivityMainBinding
    @Inject
    lateinit var homeRepoI: PlacesDataRepoI

    //12.913907, 77.638119 - bda complex,hsr layout
    private val latLng: String = "12.913907,77.638119"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        GPlacesRestaurantApp.dataComponent().inject(this)
        initVm()
        initAdapter()
        initDataProvider()
    }

    override fun provideSearch(query: String) {
        mViewModel.getRestaurantsBySearch(latLng, query)
    }

    override fun onSearchClosed() {
        //do nothing
    }

    private fun initDataProvider() {
        mViewModel.getRestaurantsByLatLng(latLng)
    }

    private fun initAdapter() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL, false
            )
            mAdapter = RestaurantAdapter()
            adapter = mAdapter
        }
    }

    private fun initVm() {
        val factory = MainActivityViewModel(
            homeRepoI,
            application
        ).createFactory()
        mViewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        mViewModel.mBaseStateObservable.observe(this, Observer {
            mDataBinding.state = it
            updateViews(it)
        })
    }

    private fun updateViews(state: MainActivityViewModelState) {
        when {
            state.success -> {
                mAdapter.clearAndSubmitList(state.data?.restaurantList ?: emptyList())
            }
            state.failure -> {
                toast(state.message)
            }
        }
    }
}