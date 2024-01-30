package com.example.examen_v1.model

import com.example.examen_v1.dao.EDatabase
import com.example.examen_v1.dao.ESqliteHelper

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

    fun agregarCancion(idCancion: Int) {
        val cancion = EDatabase.database!!.consultarCancionPorID(idCancion)
        listaCanciones!!.add(cancion!!)
        EDatabase.database!!.agregarCancionALista(id, idCancion)
        updateDuracion(cancion.getDuracion())
        EDatabase.database!!.actualizarLista(id, nombre, reproduccionAleatoria, duracion)
    }

    fun quitarCancionPorId(idCancion: Int): Boolean {
        val cancion = EDatabase.database!!.consultarCancionPorID(idCancion)
        listaCanciones!!.remove(cancion!!)
        EDatabase.database!!.quitarCancionDeLista(id, idCancion)
        updateDuracion(-cancion.getDuracion())
        EDatabase.database!!.actualizarLista(id, nombre, reproduccionAleatoria, duracion)
        return true
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

    /*
    companion object {
        var numListas: Int = 0
        var listas: ArrayList<ListaReproduccion> = arrayListOf<ListaReproduccion>()

        fun agregarLista(lista: ListaReproduccion){
            listas.add(lista)
        }

        fun quitarLista(lista: ListaReproduccion){
            listas.remove(lista)
        }

        fun deleteById(id: Int): Boolean {
            val removido = listas.removeIf { lista -> (lista.id == id) }
            return removido
        }

        fun getById(id: Int): ListaReproduccion?{
            for (item in listas) {
                if (item.id == id) {
                    println("Lista encontrada")
                    return item
                }
            }
            println("No se ha encontrado la lista.")
            return null
        }

    }
*/
}