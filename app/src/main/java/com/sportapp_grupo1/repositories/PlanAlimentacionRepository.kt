package com.sportapp_grupo1.repositories

import android.app.Application
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.NetworkServiceAdapter

class PlanAlimentacionRepository (val application: Application) {

    suspend fun addPlanAlimentacion(new: PlanAlimentacion): PlanAlimentacion {
        CacheManager.getInstance(application.applicationContext)
            .addPlanAlimentacion(new.planAlimentacionID, new)
        return NetworkServiceAdapter.getInstance(application).addPlanAlimentacion(new)
    }

}