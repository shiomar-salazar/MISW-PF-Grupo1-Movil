package com.sportapp_grupo1.models

data class PlanEntrenamiento(
    val planEntrenamientoID:String = "",
    val entrenamiento: String,
    val lunes: String,
    val martes: String,
    val miercoles: String,
    val jueves: String,
    val viernes: String,
    val sabado: String,
    val domingo: String,
    val numero_semanas: Int = 1
)