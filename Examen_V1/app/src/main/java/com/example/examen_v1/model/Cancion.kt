package com.example.examen_v1.model

class Cancion(
    private var id: Int,
    private var nombre: String,
    private var duracion: Double,
    private var favorita: Boolean,
    private var autor: String
) {

    init {
        this.id
        this.nombre
        this.duracion
        this.favorita
        this.autor
    }


    fun actualizarNombre(nombre: String) {
        this.nombre = nombre
    }

    fun actualizarFavorita(favorita: Boolean) {
        this.favorita = favorita
    }

    fun getDuracion(): Double {
        return duracion
    }

    override fun toString(): String {
        return "Cancion(id=$id, nombre='$nombre', duracion=$duracion, favorita=$favorita, autor='$autor')"
    }


    companion object {
        var numCanciones: Int = 0
        var canciones: ArrayList<Cancion> = arrayListOf()

        fun getById(id: Int): Cancion? {
            for (item in canciones) {
                if (item.id == id) {
                    println("Cancion encontrada")
                    return item
                }
            }
            println("No se ha encontrado la cancion.")
            return null
        }

    }


}