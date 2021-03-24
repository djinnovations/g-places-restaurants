package com.djphy.example.googleplacesdynamicsearch.features

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.djphy.example.googleplacesdynamicsearch.R
import com.djphy.example.googleplacesdynamicsearch.TAG
import com.djphy.example.googleplacesdynamicsearch.collapseSearch
import com.djphy.example.googleplacesdynamicsearch.features.home.SearchContract
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*


abstract class BaseSearchActivity : AppCompatActivity(),
    SearchContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Uncaught exceptions */
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e(TAG, String.format("Exception in thread : ${t.name}"))
            Log.e(TAG, ""+e.printStackTrace())
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
    override fun onPause() {
        container_toolbar.edit_search.collapseSearch()
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