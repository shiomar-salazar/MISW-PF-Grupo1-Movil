package com.sportapp_grupo1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.repositories.PlanEntrenamientoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlanEntrenamientoViewModel(application: Application) : AndroidViewModel(application){

    private val _planEntrenamientoRepository = PlanEntrenamientoRepository(application)
    private val _planEntrenamiento = MutableLiveData<PlanEntrenamiento>()

    var planEntrenamiento: LiveData<PlanEntrenamiento>
        get() = _planEntrenamiento
        set(value) {}

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        getPlanEntrenamiento()
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun addNewPlanEntrenamiento(newPlan:PlanEntrenamiento): Boolean {
        return try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    _planEntrenamientoRepository.addPlanEntrenamiento(newPlan)
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

    private fun getPlanEntrenamiento() {
        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    val data = _planEntrenamientoRepository.getPlanEntrenamiento()
                    _planEntrenamiento.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }

        } catch (e:Exception){
            _eventNetworkError.value = true
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlanEntrenamientoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlanEntrenamientoViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}