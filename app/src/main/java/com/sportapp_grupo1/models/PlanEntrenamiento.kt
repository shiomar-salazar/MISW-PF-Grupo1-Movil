package com.sportapp_grupo1.models

data class PlanEntrenamiento(
    val planEntrenamientoID:Int = 0,
    val lunes: Entrenamiento,
    val martes: Entrenamiento,
    val miercoles: Entrenamiento,
    val jueves: Entrenamiento,
    val viernes: Entrenamiento,
    val sabado: Entrenamiento,
    val domingo: Entrenamiento,
    val numero_semanas: Int
)