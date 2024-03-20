package com.sportapp_grupo1.network

import android.content.Context

class CacheManager(context: Context) {
    companion object {

        private var token: String = ""

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

}