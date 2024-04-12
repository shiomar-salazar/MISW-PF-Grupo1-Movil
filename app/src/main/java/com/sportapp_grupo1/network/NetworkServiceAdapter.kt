package com.sportapp_grupo1.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.models.User
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL_USER = "https://misw-pf-grupo1-backend-gestor-usuarios-klme3r4qta-uc.a.run.app/"
        const val BASE_URL_ENTRENAMIENTO = "https://misw-pf-grupo1-backend-gestor-entrenamientos-klme3r4qta-uc.a.run.app/"
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

    suspend fun addPlanEntrenamiento(new: PlanEntrenamiento, user: User) = suspendCoroutine { cont ->
        val params = mapOf(
            "entrenamiento" to new.entrenamiento,
            "numero_semanas" to new.numero_semanas,
            "id_usuario" to user.userId,
            "plan_entrenamiento" to mapOf<String, Any>(
                "lunes" to new.lunes,
                "martes" to new.martes,
                "miercoles" to new.miercoles,
                "jueves" to new.jueves,
                "viernes" to new.viernes,
                "sabado" to new.sabado,
                "domingo" to  new.domingo
            )
        )

        requestQueue.add(
            postRequest_token(
                "entrenamientos/plan-entrenamiento",
                JSONObject(params),
                { response ->
                     val planCreated = PlanEntrenamiento (
                        planEntrenamientoID = response.optString("planId"),
                        entrenamiento = response.optString("entrenamiento"),
                        lunes = response.optString("lunes"),
                        martes = response.optString("martes"),
                        miercoles = response.optString("miercoles"),
                        jueves = response.optString("jueves"),
                        viernes = response.optString("viernes"),
                        sabado = response.optString("sabado"),
                        domingo = response.optString("domingo"),
                        numero_semanas = response.optInt("numero_semanas")
                    )
                    cont.resume(planCreated)
                },
                {
                    cont.resumeWithException(it)
                }, BASE_URL_ENTRENAMIENTO, user.token)
        )
    }

    suspend fun getPlanEntrenamiento(user: User) = suspendCoroutine { cont ->


        requestQueue.add(
            getRequest_token(
                "entrenamientos/plan-entrenamiento/usuario/"+user.userId,
                null,
                { response ->
                    val plan = PlanEntrenamiento (
                        planEntrenamientoID = response.optString("id"),
                        entrenamiento = response.optString("entrenamiento"),
                        lunes = response.getJSONObject("plan_entrenamiento").optInt("lunes").toString(),
                        martes = response.getJSONObject("plan_entrenamiento").optInt("martes").toString(),
                        miercoles = response.getJSONObject("plan_entrenamiento").optInt("miercoles").toString(),
                        jueves = response.getJSONObject("plan_entrenamiento").optInt("jueves").toString(),
                        viernes = response.getJSONObject("plan_entrenamiento").optInt("viernes").toString(),
                        sabado = response.getJSONObject("plan_entrenamiento").optInt("sabado").toString(),
                        domingo = response.getJSONObject("plan_entrenamiento").optInt("domingo").toString(),
                        numero_semanas = response.optInt("numero_semanas")
                    )
                    cont.resume(plan)
                },
                {
                    cont.resumeWithException(it)
                }, BASE_URL_ENTRENAMIENTO, user.token)
        )

    }


    suspend fun addPlanAlimentacion(new: PlanAlimentacion) = suspendCoroutine { cont ->
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
                     val planCreated = PlanAlimentacion (
                        planAlimentacionID = response.optString("planId"),
                        lunes = response.optString("lunes"),
                        martes = response.optString("martes"),
                        miercoles = response.optString("miercoles"),
                        jueves = response.optString("jueves"),
                        viernes = response.optString("viernes"),
                        sabado = response.optString("sabado"),
                        domingo = response.optString("domingo"),
                        numero_semanas = response.optString("numero_semanas")
                    )
                    cont.resume(planCreated)
                },
                {
                    cont.resumeWithException(it)
                })
        )*/

        val planCreated = PlanAlimentacion (
            planAlimentacionID = "",
            lunes = new.lunes,
            martes = new.martes,
            miercoles = new.miercoles,
            jueves = new.jueves,
            viernes = new.viernes,
            sabado = new.sabado,
            domingo = new.domingo,
            numero_semanas = new.numero_semanas
        )
        cont.resume(planCreated)

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
            ml_agua = new.ml_agua
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
            userId = new.userId,
            actividad = new.actividad,
            distancia = new.distancia,
            tiempo = new.tiempo,
            resultado = new.resultado,
            feedback = new.feedback
        )
        cont.resume(resultCreated)

    }

    private fun postRequest_token(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener,
        URL:String,
        token:String
    ):JsonObjectRequest {
        val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, URL + path, body,
            responseListener,
            errorListener) {
            //this is the part, that adds the header to the request
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $token"
                params["content-type"] = "application/json"
                return params
            }
        }
        return jsonRequest
    }
    private fun getRequest_token(
        path: String,
        body: JSONObject?,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener,
        URL:String,
        token:String
    ):JsonObjectRequest {
        val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, URL + path, body,
            responseListener,
            errorListener) {
            //this is the part, that adds the header to the request
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Bearer $token"
                params["content-type"] = "application/json"
                return params
            }
        }
        return jsonRequest
    }



}