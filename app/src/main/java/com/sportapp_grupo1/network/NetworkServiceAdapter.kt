package com.sportapp_grupo1.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.models.Entrenamiento
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun addAlimentacion(new: Alimentacion) = suspendCoroutine { cont ->
        /*requestQueue.add(
            postRequest_token(
                "albums",
                JSONObject(
                    """{"name":"${album.name}",
                    |"cover":"${album.cover}",
                    |"releaseDate":"${album.releaseDate}",
                    |"description":"${album.description}",
                    |"genre":"${album.genre}",
                    |"recordLabel":"${album.recordLabel}"}""".trimMargin()
                ),
                { response ->
                     val resultCreated = Alimentacion (
                        alimentacionID = "",
                        calorias1 = new.calorias1,
                        calorias2 = new.calorias2,
                        calorias3 = new.calorias3,
                        ml_agua = new.ml_agua
                    )
                    cont.resume(resultCreated)
                },
                {
                    cont.resumeWithException(it)
                })
        )*/

        val resultCreated = Alimentacion (
            alimentacionID = "",
            calorias1 = new.calorias1,
            calorias2 = new.calorias2,
            calorias3 = new.calorias3,
            ml_agua = new.ml_agua,
            date = new.date,
            total_calories = (new.calorias1.toInt() + new.calorias2.toInt() + new.calorias3.toInt()).toString()
        )
        cont.resume(resultCreated)

    }

    suspend fun addEntrenamiento(new: Entrenamiento) = suspendCoroutine { cont ->
        /*requestQueue.add(
            postRequest_token(
                "albums",
                JSONObject(
                    """{"name":"${album.name}",
                    |"cover":"${album.cover}",
                    |"releaseDate":"${album.releaseDate}",
                    |"description":"${album.description}",
                    |"genre":"${album.genre}",
                    |"recordLabel":"${album.recordLabel}"}""".trimMargin()
                ),
                { response ->
                     val resultCreated = Entrenamiento (
                                        entrenamientoId = "",
                                        userId = new.userId,
                                        actividad = new.actividad,
                                        distancia = new.distancia,
                                        tiempo = new.tiempo,
                                        resultado = new.resultado,
                                        feedback = new.feedback
                                    )
                    cont.resume(resultCreated)
                },
                {
                    cont.resumeWithException(it)
                })
        )*/

        val resultCreated = Entrenamiento (
            entrenamientoId = "",
            actividad = new.actividad,
            distancia = new.distancia,
            tiempo = new.tiempo,
            resultado = new.resultado,
            feedback = new.feedback,
            date = ""
        )
        cont.resume(resultCreated)

    }


}