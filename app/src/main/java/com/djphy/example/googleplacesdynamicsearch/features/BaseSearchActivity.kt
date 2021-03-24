package com.djphy.example.googleplacesdynamicsearch.features

import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import com.djphy.example.googleplacesdynamicsearch.*
import com.djphy.example.googleplacesdynamicsearch.features.home.SearchContract
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.yayandroid.locationmanager.base.LocationBaseActivity
import com.yayandroid.locationmanager.configuration.Configurations
import com.yayandroid.locationmanager.configuration.LocationConfiguration
import com.yayandroid.locationmanager.constants.FailType
import com.yayandroid.locationmanager.constants.ProcessType
import de.mateware.snacky.Snacky
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*


abstract class BaseSearchActivity : LocationBaseActivity(),
    SearchContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Uncaught exceptions */
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e(TAG, String.format("Exception in thread : ${t.name}"))
            Log.e(TAG, "" + e.printStackTrace())
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolbar()
        initSearch()
    }

    private fun initSearch() {
        container_toolbar.edit_search.setOnQueryTextListener(searchQueryTextKeyListener)
        container_toolbar.edit_search.setOnSearchViewListener(onSearchViewListener)
    }

    protected fun initLocation() {
        getLocation()
    }

    override fun getLocationConfiguration(): LocationConfiguration? {
        return Configurations.defaultConfiguration(
            "Permission Required",
            "Would you mind to turn GPS on?"
        )
    }

    override fun onLocationChanged(location: Location) {
        dismissProgress()
        Log.d(TAG, "latLng: ${location.latitude} & ${location.longitude}")
    }

    override fun onLocationFailed(@FailType failType: Int) {
        dismissProgress()
        when (failType) {
            FailType.TIMEOUT -> {
                infoSnacky("Couldn't get location, and timeout!")
            }
            FailType.PERMISSION_DENIED -> {
                toast("Couldn't get location, permission denied")
                locationPermissionDeniedAction()
            }
            FailType.NETWORK_NOT_AVAILABLE -> {
                infoSnacky("Couldn't get location, No network")
            }
            else -> {
                infoSnacky("Couldn't get location")
            }
        }
    }

    override fun onProcessTypeChanged(processType: Int) {
        when (processType) {
            ProcessType.GETTING_LOCATION_FROM_GOOGLE_PLAY_SERVICES,
            ProcessType.GETTING_LOCATION_FROM_GPS_PROVIDER,
            ProcessType.GETTING_LOCATION_FROM_NETWORK_PROVIDER -> {
                infoSnacky("Fetching Location, hang on..")
            }
            else -> {
                //do nothing
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.clear()
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.item_search)
        searchItem?.let {
            container_toolbar.edit_search.setMenuItem(it)
        }
        return true
    }

    private val searchQueryTextKeyListener =
        object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                updateDataFromInput(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                updateDataFromInput(query)
                return true
            }
        }

    private val onSearchViewListener =
        object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                onSearchClosed()
            }

            override fun onSearchViewShown() {
                // donothing
            }
        }

    private fun updateDataFromInput(key: String?) {
        key?.let { txt ->
            Handler(Looper.getMainLooper()).apply {
                mSearchRunnable.let {
                    removeCallbacks(it)
                    it.searchString = txt
                    postDelayed(it, 500)
                }
            }
        }
    }

    private val mSearchRunnable = object : Runnable {
        var searchString = ""
        override fun run() {
            provideSearch(searchString)
        }
    }

    private fun initToolbar() {
        container_toolbar.toolbar.apply {
            setSupportActionBar(this)
            title = getString(R.string.title)
            setToolBarBackAction()
        }
    }

    //subclass can override to disable back icon
    open fun Toolbar.setToolBarBackAction() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setNavigationOnClickListener {
            onBackPressed()
        }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        if (locationManager.isWaitingForLocation
            && !locationManager.isAnyDialogShowing
        ) {
            displayProgress()
        }
    }

    @CallSuper
    override fun onPause() {
        container_toolbar.edit_search.collapseSearch()
        dismissProgress()
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        container_toolbar.edit_search.collapseSearch()
        super.onStop()
    }

    override fun onBackPressed() {
        if (container_toolbar.edit_search.isSearchOpen) {
            container_toolbar.edit_search.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

}