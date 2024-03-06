package com.example.proyecto_02.entities

data class Entrada(
    var id: String,
    var fecha: String,
    var hora: String,
    var titulo: String,
    var contenido: String,
    var clima: Clima,
    var temperatura: Double,
    var ubicacion: String,
    var sentimiento: Sentimiento
) {

    enum class Sentimiento{
        Feliz, Neutral, Triste
    }

    enum class Clima{
        Soleado, Nuboso, Lluvioso
    }
}