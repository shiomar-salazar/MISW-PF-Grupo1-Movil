package com.sportapp_grupo1.models

data class Entrenamiento (
    val entrenamientoId: Int = 0,
    val userId: Int = 0,
    val actividad:Int,
    val distancia: String,
    val tiempo:String,
    val resultado:String, //FTP o Vo2Max
    val feedback:String
)