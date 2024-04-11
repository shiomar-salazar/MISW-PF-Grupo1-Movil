package com.sportapp_grupo1.repositories

import android.app.Application
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.NetworkServiceAdapter

class PlanEntrenamientoRepository(val application: Application) {

    suspend fun addPlanEntrenamiento (new: PlanEntrenamiento):PlanEntrenamiento{
        CacheManager.getInstance(application.applicationContext).addPlanEntrentamiento(new)
        var user = CacheManager.getInstance(application.applicationContext).getUsuario()
        return NetworkServiceAdapter.getInstance(application).addPlanEntrenamiento(new, user)
    }
}