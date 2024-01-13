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
        id ?: numListas++,
        nombre,
        reproduccionAleatoria ?: false,
        duracion ?: 0.0,
        listaCanciones ?: arrayListOf<Cancion>()
    )

    fun getListaCanciones(): ArrayList<Cancion>? {
        return listaCanciones
    }

    fun isAleatoria(): Boolean {
        return reproduccionAleatoria
    }

    fun agregarCancion(idCancion: Int) {
        val cancion = Cancion.getById(idCancion)
        listaCanciones!!.add(cancion!!)
        updateDuracion(cancion.getDuracion())
    }

    fun quitarCancionPorId(idCancion: Int) {
        val cancion = Cancion.getById(idCancion)
        listaCanciones!!.remove(cancion!!)
        updateDuracion(-cancion.getDuracion())
    }

    fun cambiarNombre(nombre: String) {
        this.nombre = nombre
    }

    fun reproduccionAleatoria(value: Boolean) {
        this.reproduccionAleatoria = value
    }

    fun mostrarCanciones() {
        listaCanciones!!.forEach { println(it) }
    }

    private fun updateDuracion(duracion: Double) {
        this.duracion + duracion
    }

    override fun toString(): String {
        return "ListaReproduccion(id=$id, nombre='$nombre', reproduccionAleatoria=$reproduccionAleatoria, duracion=$duracion, listaCanciones=$listaCanciones)"
    }

    companion object {
        var numListas: Int = 0
    }

}