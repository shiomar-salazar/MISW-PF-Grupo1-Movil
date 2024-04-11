package com.sportapp_grupo1.models

data class User(
    val userId:String = "",
    val token: String,
    val nombres:String,
    val rol: String,
    val plan: String

)