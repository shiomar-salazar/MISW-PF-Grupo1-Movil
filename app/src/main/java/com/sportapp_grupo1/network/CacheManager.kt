package com.sportapp_grupo1.network

import android.content.Context
import android.util.Log
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.models.User

class CacheManager(context: Context) {
    companion object {

        private var usuario = User (
            userId = "",
            nombres = "",
            rol = "",
            plan = "",
            token = ""
        )
        private var planEntrenamiento: PlanEntrenamiento = PlanEntrenamiento(
            planEntrenamientoID = "",
            entrenamiento = "Otro",
            lunes = "",
            martes = "",
            miercoles = "",
            jueves = "",
            viernes = "",
            sabado = "",
            domingo = "",
            numero_semanas = 1
        )
        private var planAlimentacion: PlanAlimentacion = PlanAlimentacion(
            planAlimentacionID = "df4cf616-f784-11ee-bea5-2528b3b3fb6c",
            lunes = "1200",
            martes = "1500",
            miercoles = "2000",
            jueves = "2000",
            viernes = "1800",
            sabado = "1600",
            domingo = "1800",
            numero_semanas = 12
        )
        private var alimentacionResults: List<Alimentacion> = mutableListOf()
        private var entrenamientoResults: List<Entrenamiento> = mutableListOf()


        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    fun saveUsuario (user: User){
        Log.d("SaveUsuario", "Se guarda usario con ID:  ${user.userId}")
        Log.d("SaveUsuario", "Se guarda usario con token:  ${user.token}")
        usuario = user
    }

    fun getUsuario():User {
        return usuario
    }

    fun addPlanEntrentamiento(new:PlanEntrenamiento){
        planEntrenamiento = new
    }

    fun getPlanEntrenamiento(): PlanEntrenamiento {
        Log.d("getPlanEntrenamiento", planEntrenamiento.planEntrenamientoID)
        return planEntrenamiento
    }

    fun addPlanAlimentacion(new:PlanAlimentacion){
        planAlimentacion = new
    }

    fun addAlimentacion( new:Alimentacion){
        alimentacionResults = alimentacionResults.plus(new)
    }

    fun addEntrenamiento( new:Entrenamiento){
        entrenamientoResults = entrenamientoResults.plus(new)
    }

}