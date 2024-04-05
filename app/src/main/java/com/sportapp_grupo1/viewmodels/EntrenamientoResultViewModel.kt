package com.sportapp_grupo1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.repositories.EntrenamientoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EntrenamientoResultViewModel (application: Application) : AndroidViewModel(application){

    private val _entrenamientoRepository = EntrenamientoRepository(application)
    private val _entrenamiento = MutableLiveData<Entrenamiento>()

    var entrenamiento: LiveData<Entrenamiento>
        get() = _entrenamiento
        set(value) {}

    private var _eventNetworkError = MutableLiveData(false)

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

    fun addNewEntrenamientoResult(newResult: Entrenamiento): Boolean {
        return try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    _entrenamientoRepository.addEntrenamiento(newResult)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
            true
        } catch (e:Exception){
            _eventNetworkError.value = true
            false
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EntrenamientoResultViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EntrenamientoResultViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}