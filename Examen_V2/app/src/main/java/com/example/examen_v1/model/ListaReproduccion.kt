package com.example.examen_v1.model


class ListaReproduccion(
    private var id: Int,
    private var nombre: String,
    private var reproduccionAleatoria: Boolean,
    private var duracion: Double,
    private var listaCanciones: ArrayList<Cancion>?
) {
    init {
        this.id
        this.nombre
        this.reproduccionAleatoria
        this.duracion
        this.listaCanciones
    }

    constructor(
        id: Int?,
        nombre: String,
        reproduccionAleatoria: Boolean?,
        duracion: Double?,
        listaCanciones: ArrayList<Cancion>?
    ) : this( //lectura
        id ?: 0,
        nombre,
        reproduccionAleatoria ?: false,
        duracion ?: 0.0,
        listaCanciones ?: arrayListOf<Cancion>()
    )

    fun getId():Int {
        return id
    }
    fun getNombre():String {
        return nombre
    }

    fun getListaCanciones(): ArrayList<Cancion>? {
        return listaCanciones
    }

    fun isAleatoria(): Boolean {
        return reproduccionAleatoria
    }

    fun getDuracion():Double {
        return duracion
    }

    fun setId(id: Int){
        this.id = id
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setReproduccionAleatoria(value: Boolean) {
        this.reproduccionAleatoria = value
    }

    fun setDuracion(duracion: Double){
        this.duracion = duracion
    }

    fun mostrarCanciones() {
        listaCanciones!!.forEach { println(it) }
    }

    private fun updateDuracion(duracion: Double) {
        this.duracion += duracion
    }

    override fun toString(): String {
        return "ListaReproduccion(id=$id, nombre='$nombre', reproduccionAleatoria=$reproduccionAleatoria, duracion=$duracion, listaCanciones=$listaCanciones)"
    }

    fun setListaDeCanciones(alc: ArrayList<Cancion>) {
        this.listaCanciones = alc
    }

}