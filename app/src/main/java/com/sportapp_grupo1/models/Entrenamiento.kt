package com.sportapp_grupo1.models

data class Entrenamiento (
    val entrenamientoId: String = "",
    val actividad:String,
    val distancia: String,
    val tiempo:String,
    val resultado:String, //FTP o Vo2Max
    val feedback:String,
    val date:String
)