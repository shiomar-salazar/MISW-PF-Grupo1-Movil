package com.sportapp_grupo1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EntrenamientoMenuViewModel(application: Application) : AndroidViewModel(application) {

    private var _eventNetworkError = MutableLiveData(false)
    private val _full_access = MutableLiveData<Boolean>(false)

    var full_access: LiveData<Boolean>
        get() = _full_access
        set(value) {}

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EntrenamientoMenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EntrenamientoMenuViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}