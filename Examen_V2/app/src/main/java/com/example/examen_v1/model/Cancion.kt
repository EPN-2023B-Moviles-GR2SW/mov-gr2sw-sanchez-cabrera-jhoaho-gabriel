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
        id?: 0,
        nombre,
        duracion,
        favorita,
        autor
    )

    fun setId(id: Int){
        this.id = id
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setFavorita(favorita: Boolean) {
        this.favorita = favorita
    }

    fun setDuracion(duracion: Double){
        this.duracion = duracion
    }

    fun setAutor(autor: String){
        this.autor = autor
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

    fun getAutor(): String {
        return autor
    }

    fun isFavorita(): Boolean{
        return favorita
    }

    override fun toString(): String {
        return "Cancion(id=$id, nombre='$nombre', duracion=$duracion, favorita=$favorita, autor='$autor')"
    }

}