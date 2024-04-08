package com.sportapp_grupo1.repositories

import android.app.Application
import android.util.Log
import com.sportapp_grupo1.models.User
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.NetworkServiceAdapter

class LoginRepository(val application: Application) {

    suspend fun login(username:String, password:String): String  {
        val potentialResp =
            CacheManager.getInstance(application.applicationContext).getUsuario().token
        return if (potentialResp == "") {
            Log.d("Cache decision", "get Token from network")
            val user:User = NetworkServiceAdapter.getInstance(application).login(username, password)
            CacheManager.getInstance(application.applicationContext).saveUsuario(user)
            user.token
        } else {
            Log.d("Cache decision", "return Token from cache")
            potentialResp
        }
    }


}