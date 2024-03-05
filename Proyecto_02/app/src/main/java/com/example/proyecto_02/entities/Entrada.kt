package com.example.proyecto_02.entities

class Entrada(
    var id: String,
    var fecha: String,
    var hora: String,
    var titulo: String,
    var contenido: String,
    var clima: String,
    var temperatura: Int,
    var ubicacion: String,
    var sentimiento: Sentimiento
) {

    enum class Sentimiento{
        Feliz, Neutral, Triste
    }
}