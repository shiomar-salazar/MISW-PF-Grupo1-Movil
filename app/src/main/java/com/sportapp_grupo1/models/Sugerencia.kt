package com.sportapp_grupo1.models

data class Sugerencia(
    val sugerencia_id:String,
    val nombre: String,
    val lugar: String,
    val fecha: String = "",
    val costo:String,
    val descripcion:String = "",
    val estado:String = "",
    val frecuencia: String =" ",
    val horario:List<String> = emptyList(),
    val horario_final:String = ""
)
