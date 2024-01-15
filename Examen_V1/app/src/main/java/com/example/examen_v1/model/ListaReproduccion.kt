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
    ){
        ListaReproduccion.agregarLista(this)
    }

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
        val cancion = Cancion.getById(idCancion)
        listaCanciones!!.add(cancion!!)
        updateDuracion(cancion.getDuracion())
    }

    fun quitarCancionPorId(idCancion: Int): Boolean {
        val cancion = Cancion.getById(idCancion)
        listaCanciones!!.remove(cancion!!)
        updateDuracion(-cancion.getDuracion())
        return true
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
        this.duracion += duracion
    }

    override fun toString(): String {
        return "ListaReproduccion(id=$id, nombre='$nombre', reproduccionAleatoria=$reproduccionAleatoria, duracion=$duracion, listaCanciones=$listaCanciones)"
    }

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

}