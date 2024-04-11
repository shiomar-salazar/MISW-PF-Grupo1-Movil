package com.sportapp_grupo1.repositories

import android.app.Application
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.NetworkServiceAdapter

class EntrenamientoRepository (val application: Application) {

    suspend fun addEntrenamiento(new: Entrenamiento): Entrenamiento {
        CacheManager.getInstance(application.applicationContext)
            .addEntrenamiento(new)
        return NetworkServiceAdapter.getInstance(application).addEntrenamiento(new)
    }
}