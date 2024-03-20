package com.sportapp_grupo1.models

data class User(
    val userId:Int = 0,
    val nombre:String,
    val apellido:String,
    val tipo_identificacion:String,
    val numero_identificacion:String,
    val genero:String,
    val edad:Int,
    val peso:Float,
    val altura:Float,
    val pais_nacimiento:String,
    val ciudad_nacimiento:String,
    val pais_residencia:String,
    val ciudad_residencia:String,
    val antiguedad_residencia:String,
    val deporte_a_practicar:String,
    val planEntrenamiento: PlanEntrenamiento?,
    val planAlimentacion: PlanAlimentacion?
)