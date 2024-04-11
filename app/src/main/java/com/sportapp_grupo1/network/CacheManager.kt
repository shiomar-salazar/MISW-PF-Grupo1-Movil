package com.sportapp_grupo1.network

import android.content.Context
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.models.User

class CacheManager(context: Context) {
    companion object {

        private var usuario = User (
            userId = 0,
            nombres = "",
            rol = "",
            plan = "",
            token = ""
        )
        private var planEntrenamiento: HashMap<Int, PlanEntrenamiento> = hashMapOf()
        private var planesEntrenamiento: List<PlanEntrenamiento> = mutableListOf()
        private var planAlimentacion: HashMap<Int, PlanAlimentacion> = hashMapOf()
        private var planesAlimentacion: List<PlanAlimentacion> = mutableListOf()
        private var alimentacionResult: HashMap<Int, Alimentacion> = hashMapOf()
        private var alimentacionResults: List<Alimentacion> = mutableListOf()
        private var entrenamientoResult: HashMap<Int, Entrenamiento> = hashMapOf()
        private var entrenamientoResults: List<Entrenamiento> = mutableListOf()


        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    fun saveUsuario (user:User){
        usuario = user
    }

    fun getUsuario():User {
        return usuario
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

    fun addAlimentacion(resultId: Int, new:Alimentacion){
        if (!alimentacionResult.containsKey(resultId)) {
            alimentacionResult[resultId] = new
        }
        alimentacionResults = alimentacionResults.plus(new)
    }

    fun addEntrenamiento(resultId: Int, new:Entrenamiento){
        if (!entrenamientoResult.containsKey(resultId)) {
            entrenamientoResult[resultId] = new
        }
        entrenamientoResults = entrenamientoResults.plus(new)
    }

}