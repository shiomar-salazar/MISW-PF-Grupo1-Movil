package com.sportapp_grupo1.repositories

import android.app.Application
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.NetworkServiceAdapter

class AlimentacionRepository (val application: Application) {

    suspend fun addAlimentacion(new: Alimentacion): Alimentacion {
        CacheManager.getInstance(application.applicationContext)
            .addAlimentacion(new)
        return NetworkServiceAdapter.getInstance(application).addAlimentacion(new)
    }
}