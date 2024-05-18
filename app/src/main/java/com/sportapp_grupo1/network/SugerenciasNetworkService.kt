package com.sportapp_grupo1.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class SugerenciasNetworkService(context: Context) {

    val instance: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object {
        const val URL =
            "https://misw-pf-grupo1-backend-gestor-consultas-klme3r4qta-uc.a.run.app/consultas/servicios"
        const val URL_agendar =
            "https://misw-pf-grupo1-backend-gestor-servicios-klme3r4qta-uc.a.run.app/servicios/agendar"
        const val URL_agendados =
            "https://misw-pf-grupo1-backend-gestor-consultas-klme3r4qta-uc.a.run.app/consultas/servicios/agendados"

        fun getRequest(
            responseListener: Response.Listener<JSONArray>,
            errorListener: Response.ErrorListener, token: String
        ):
                JsonArrayRequest {
            val jsonRequest: JsonArrayRequest = object : JsonArrayRequest(
                Method.GET, URL, null,
                responseListener,
                errorListener
            ) {
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

        fun getRequest_single(
            responseListener: Response.Listener<JSONObject>,
            errorListener: Response.ErrorListener,sugerenciaId: String, token: String
        ):
                JsonObjectRequest {
            val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.GET, "$URL/$sugerenciaId", null,
                responseListener,
                errorListener
            ) {
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

        fun postRequest_agendar(
            body: JSONObject, responseListener: Response.Listener<JSONObject>,
            errorListener: Response.ErrorListener, token: String
        ):
                JsonObjectRequest {
            val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, URL_agendar, body,
                responseListener,
                errorListener
            ) {
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

        fun getRequest_registrados(
            responseListener: Response.Listener<JSONArray>,
            errorListener: Response.ErrorListener, token: String
        ):
                JsonArrayRequest {
            val jsonRequest: JsonArrayRequest = object : JsonArrayRequest(
                Method.GET, URL_agendados, null,
                responseListener,
                errorListener
            ) {
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
}