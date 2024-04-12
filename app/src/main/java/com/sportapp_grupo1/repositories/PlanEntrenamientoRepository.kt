package com.sportapp_grupo1.repositories

import android.app.Application
import android.util.Log
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.NetworkServiceAdapter

class PlanEntrenamientoRepository(val application: Application) {

    suspend fun addPlanEntrenamiento (new: PlanEntrenamiento):PlanEntrenamiento{
        CacheManager.getInstance(application.applicationContext).addPlanEntrentamiento(new)
        var user = CacheManager.getInstance(application.applicationContext).getUsuario()
        return NetworkServiceAdapter.getInstance(application).addPlanEntrenamiento(new, user)
    }

    suspend fun getPlanEntrenamiento(): PlanEntrenamiento {
        var user = CacheManager.getInstance(application.applicationContext).getUsuario()
        val potentialResp = CacheManager.getInstance(application.applicationContext).getPlanEntrenamiento()
        return if (potentialResp.planEntrenamientoID == "") {
            Log.d("Cache decision", "get from network")
            val planEnt = NetworkServiceAdapter.getInstance(application).getPlanEntrenamiento(user)
            CacheManager.getInstance(application.applicationContext).addPlanEntrentamiento(planEnt)
            planEnt
        } else {
            Log.d("Cache decision", "return ${potentialResp.planEntrenamientoID} elements from cache")
            potentialResp
        }

    }
}