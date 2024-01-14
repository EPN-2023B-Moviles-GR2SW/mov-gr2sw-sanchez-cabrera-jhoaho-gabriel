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

    constructor(id: Int?, nombre: String, duracion: Double, favorita: Boolean, autor: String): this(
        id?: numCanciones++,
        nombre,
        duracion,
        favorita,
        autor
    ){
        Cancion.agregarCancion(this)
    }


    fun actualizarNombre(nombre: String) {
        this.nombre = nombre
    }

    fun actualizarFavorita(favorita: Boolean) {
        this.favorita = favorita
    }

    fun getId():Int{
        return id
    }
    fun getNombre(): String {
        return nombre
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

        fun agregarCancion(cancion: Cancion){
            Cancion.canciones.add(cancion)
        }

        fun quitarCancion(cancion: Cancion){
            Cancion.canciones.remove(cancion)
        }

        fun getById(id: Int): Cancion? {
            for (item in canciones) {
                if (item.id == id) {
                    return item
                }
            }
            return null
        }

    }

}