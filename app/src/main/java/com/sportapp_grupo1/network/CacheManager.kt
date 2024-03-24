package com.sportapp_grupo1.network

import android.content.Context
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.models.PlanEntrenamiento

class CacheManager(context: Context) {
    companion object {

        private var token: String = ""
        private var planEntrenamiento: HashMap<Int, PlanEntrenamiento> = hashMapOf()
        private var planesEntrenamiento: List<PlanEntrenamiento> = mutableListOf()
        private var planAlimentacion: HashMap<Int, PlanAlimentacion> = hashMapOf()
        private var planesAlimentacion: List<PlanAlimentacion> = mutableListOf()


        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    fun saveToken (newToken:String){
        token = newToken
    }

    fun getToken():String {
        return token
    }

    fun addPlanEntrentamiento(planId: Int, new:PlanEntrenamiento){
        if (!planEntrenamiento.containsKey(planId)) {
            planEntrenamiento[planId] = new
        }
        planesEntrenamiento = planesEntrenamiento.plus(new)
    }

    fun addPlanAlimentacion(planId: Int, new:PlanAlimentacion){
        if (!planAlimentacion.containsKey(planId)) {
            planAlimentacion[planId] = new
        }
        planesAlimentacion = planesAlimentacion.plus(new)
    }

}