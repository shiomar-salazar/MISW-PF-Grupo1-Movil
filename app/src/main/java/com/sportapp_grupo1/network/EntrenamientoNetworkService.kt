package com.sportapp_grupo1.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class EntrenamientoNetworkService(context: Context) {

    val instance: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object {
        const val URL_E = "https://misw-pf-grupo1-backend-gestor-entrenamientos-klme3r4qta-uc.a.run.app/"
        const val URL_Consulta = "https://misw-pf-grupo1-backend-gestor-consultas-klme3r4qta-uc.a.run.app/consultas/"
        const val URL_Alerta = "https://misw-pf-grupo1-backend-gestor-entrenamientos-klme3r4qta-uc.a.run.app/entrenamientos/alerta"

        fun postRequest(
            body: JSONObject, responseListener: Response.Listener<JSONObject>,
            errorListener: Response.ErrorListener, path: String, token: String
        ):
                JsonObjectRequest {
            val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, URL_E + path, body,
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

        fun getRequest(
            responseListener: Response.Listener<JSONArray>,
            errorListener: Response.ErrorListener, path: String, token: String
        ):
                JsonArrayRequest {
            val jsonRequest: JsonArrayRequest = object : JsonArrayRequest(
                Method.GET, URL_Consulta + path, null,
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

        fun postRequestAlerta(
            body: JSONObject, responseListener: Response.Listener<JSONObject>,
            errorListener: Response.ErrorListener, token: String
        ):
                JsonObjectRequest {
            val jsonRequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, URL_Alerta, body,
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