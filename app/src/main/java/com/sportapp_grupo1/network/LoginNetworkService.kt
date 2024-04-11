package com.sportapp_grupo1.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginNetworkService constructor(context: Context) {

    val instance: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object {
        const val URL_LOGIN = "https://misw-pf-grupo1-backend-gestor-usuarios-klme3r4qta-uc.a.run.app/usuarios/login"

        fun postRequest(body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
            return  JsonObjectRequest(Request.Method.POST, URL_LOGIN, body, responseListener, errorListener)
        }

    }




}