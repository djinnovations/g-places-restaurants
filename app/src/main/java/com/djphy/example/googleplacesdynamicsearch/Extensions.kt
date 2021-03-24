package com.djphy.example.googleplacesdynamicsearch

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miguelcatalan.materialsearchview.MaterialSearchView
import de.mateware.snacky.Snacky

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
    val viewModel = this
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
    }
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun String.isValid(): Boolean {
    return this.isNotEmpty() && this.isNotBlank()
}

fun MaterialSearchView.collapseSearch() {
    if (this.isSearchOpen) {
        this.closeSearch()
    }
}

 fun AppCompatActivity.locationPermissionDeniedAction(){
    Snacky.builder().apply {
        setActivity(this@locationPermissionDeniedAction)
        setText("Goto app Setting and provide location permission")
        setTextSize(12f)
        setMaxLines(1)
        setDuration(Snacky.LENGTH_INDEFINITE)
    }.warning().show()
}

fun AppCompatActivity.infoSnacky(info: String){
    Snacky.builder().apply {
        setActivity(this@infoSnacky)
        setText(info)
        setTextSize(12f)
        setMaxLines(1)
        setDuration(Snacky.LENGTH_SHORT)
    }.info().show()
}