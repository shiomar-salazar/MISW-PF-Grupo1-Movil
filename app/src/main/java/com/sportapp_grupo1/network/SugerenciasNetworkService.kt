package com.sportapp_grupo1.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class SugerenciasNetworkService constructor(context: Context) {

    val instance: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object {
        const val URL =
            "https://misw-pf-grupo1-backend-gestor-consultas-klme3r4qta-uc.a.run.app/consultas/servicios"

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
    }
}