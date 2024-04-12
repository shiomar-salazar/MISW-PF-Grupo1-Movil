package com.sportapp_grupo1.repositories

import android.app.Application
import android.util.Log
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.NetworkServiceAdapter

class PlanAlimentacionRepository (val application: Application) {

    suspend fun addPlanAlimentacion(new: PlanAlimentacion): PlanAlimentacion {
        CacheManager.getInstance(application.applicationContext)
            .addPlanAlimentacion(new)
        return NetworkServiceAdapter.getInstance(application).addPlanAlimentacion(new)
    }

    suspend fun getPlanAlimentacion(): PlanAlimentacion {
        var user = CacheManager.getInstance(application.applicationContext).getUsuario()
        val potentialResp =
            CacheManager.getInstance(application.applicationContext).getPlanAlimentacion()
        return if (potentialResp.lunes == "") {
            Log.d("Cache decision", "get from network")
            val planAli = NetworkServiceAdapter.getInstance(application).getPlanAlimentacion(user)
            CacheManager.getInstance(application.applicationContext).addPlanAlimentacion(planAli)
            planAli
        } else {
            Log.d(
                "Cache decision",
                "return Entrenamiento with Lunes: ${potentialResp.lunes} from cache"
            )
            potentialResp
        }
    }

}