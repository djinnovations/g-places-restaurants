package com.djphy.example.googleplacesdynamicsearch.features.home

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.djphy.example.googleplacesdynamicsearch.*
import com.djphy.example.googleplacesdynamicsearch.databinding.ActivityMainBinding
import com.djphy.example.googleplacesdynamicsearch.features.BaseSearchActivity
import com.djphy.example.googleplacesdynamicsearch.repo.PlacesDataRepoI
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseSearchActivity(), View.OnClickListener{

    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mAdapter: RestaurantAdapter
    private lateinit var mDataBinding: ActivityMainBinding

    @Inject
    lateinit var homeRepoI: PlacesDataRepoI

    //12.913907, 77.638119 - bda complex,hsr layout
    private var latLng: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        GPlacesRestaurantApp.dataComponent().inject(this)
        initVm()
        initAdapter()
        nextState(MainActivityEventState.HomeLaunchedState)
    }

    private fun nextState(state: MainActivityEventState) {
        when (state) {
            is MainActivityEventState.HomeLaunchedState -> {
                initLocation()
            }

            is MainActivityEventState.NoLocationAvailableState -> {
               mViewModel.updateNoLocation()
            }

            is MainActivityEventState.InitPlacesState -> {
                initDataProvider()
            }

            is MainActivityEventState.SearchRestaurantState ->{
                latLng?.let {
                    mViewModel.getRestaurantsBySearch(it, state.searchKey)
                }
            }

            is MainActivityEventState.TryWithSampleState -> {
                resetLatLng()
                initDataProvider()
            }
        }
    }

    private fun resetLatLng(){
        latLng = "12.913907,77.638119"
    }

    override fun onLocationChanged(location: Location) {
        super.onLocationChanged(location)
        latLng = "${location.latitude},${location.longitude}"
        nextState(MainActivityEventState.InitPlacesState)
    }

    override fun onLocationFailed(failType: Int) {
        super.onLocationFailed(failType)
        latLng?: nextState(MainActivityEventState.NoLocationAvailableState)
    }

    override fun provideSearch(query: String) {
        nextState(MainActivityEventState.SearchRestaurantState(query))
    }

    override fun onSearchClosed() {
        //do nothing
    }

    private fun initDataProvider() {
        latLng?.let {
            mViewModel.getRestaurantsByLatLng(it)
        }
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
        setClickListener()
    }

    private fun setObservers() {
        mViewModel.mBaseStateObservable.observe(this, Observer {
            mDataBinding.state = it
            updateViews(it)
        })
    }

    private fun setClickListener(){
        btnTrySample.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnTrySample ->{
                nextState(MainActivityEventState.TryWithSampleState)
            }
        }
    }

    override fun dismissProgress() {
        pBInit.visibility = View.GONE
    }

    override fun displayProgress() {
        pBInit.visibility = View.VISIBLE
    }
}