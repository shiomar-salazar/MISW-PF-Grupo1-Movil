package com.sportapp_grupo1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.repositories.PlanAlimentacionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlanAlimentacionViewModel (application: Application) : AndroidViewModel(application){

    private val _planAlimentacionRepository = PlanAlimentacionRepository(application)
    private val _planAlimentacion = MutableLiveData<PlanAlimentacion>()

    var planAplimentacion: LiveData<PlanAlimentacion>
        get() = _planAlimentacion
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

    fun addNewPlanAlimentacion(newPlan: PlanAlimentacion): Boolean {
        return try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    _planAlimentacionRepository.addPlanAlimentacion(newPlan)
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
            if (modelClass.isAssignableFrom(PlanAlimentacionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlanAlimentacionViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}