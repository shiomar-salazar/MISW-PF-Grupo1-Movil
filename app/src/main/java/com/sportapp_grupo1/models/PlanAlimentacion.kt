package com.sportapp_grupo1.models

data class PlanAlimentacion (
    val planAlimentacionID: Int = 0,
    val objetivo: Int,
    val lunes: Alimentacion,
    val martes: Alimentacion,
    val miercoles: Alimentacion,
    val jueves: Alimentacion,
    val viernes: Alimentacion,
    val sabado: Alimentacion,
    val domingo: Alimentacion,
    val numero_semanas: Int

)