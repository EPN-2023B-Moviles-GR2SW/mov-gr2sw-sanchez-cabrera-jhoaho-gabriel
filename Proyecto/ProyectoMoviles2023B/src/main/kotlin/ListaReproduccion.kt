import java.io.File

class ListaReproduccion() {
    private var id: Int = 0
    private var nombre: String = ""
    private var reproduccionAleatoria: Boolean = false
    private var duracion: Double = 0.0
    private var listaCanciones: ArrayList<Cancion> = arrayListOf<Cancion>()

    constructor(
        id: Int,
        nombre: String,
        reproduccionAleatoria: Boolean?,
        duracion: Double?
    ) : this() { //desde lectura de archivos
        this.id = id
        numListas++
        this.nombre = nombre
        if (reproduccionAleatoria == null) false else this.reproduccionAleatoria = reproduccionAleatoria
        if (duracion == null) 0.0 else this.duracion = duracion
    }

    constructor(nombre: String, reproduccionAleatoria: Boolean?, duracion: Double?) : this() { //para creacion directa
        this.id = numListas++
        this.nombre = nombre
        if (reproduccionAleatoria == null) false else this.reproduccionAleatoria = reproduccionAleatoria
        if (duracion == null) 0.0 else this.duracion = duracion
    }

    fun getListaCanciones():ArrayList<Cancion>{
        return this.listaCanciones
    }

    fun isAleatoria(): Boolean{
        return this.reproduccionAleatoria
    }

    fun agregarCancion(idCancion: Int) {
        val cancion = Cancion.getById(idCancion)
        this.listaCanciones.add(cancion)
        updateList(this)
    }

    fun quitarCancionPorId(idCancion: Int) {
        val cancion = Cancion.getById(idCancion)
        this.listaCanciones.remove(cancion)
        updateList(this)
    }

    fun cambiarNombre(nombre: String) {
        this.nombre = nombre
        updateList(this)
    }

    fun reproduccionAleatoria(value: Boolean) {
        this.reproduccionAleatoria = value
        updateList(this)
    }

    fun mostrarCanciones(){
        listaCanciones.forEach{ println(it) }
    }

    override fun toString(): String {
        return "ListaReproduccion(id=$id, nombre='$nombre', reproduccionAleatoria=$reproduccionAleatoria, duracion=$duracion, listaCanciones=$listaCanciones)"
    }

    companion object {
        var numListas: Int = 0
        var listas:ArrayList<ListaReproduccion> = arrayListOf()
        val listasData: File =
            File("src/main/kotlin/data/listas.txt").apply { takeIf { !exists() }?.run { createNewFile() } }

        fun crearLista(nombre: String, reproduccionAleatoria: Boolean?, duracion: Double?) { //creacion directa
            val lista = ListaReproduccion(nombre, reproduccionAleatoria, duracion)
            listas.add(lista)
            val nl: String = "${lista.id}:" + "${lista.nombre}:" + "${lista.reproduccionAleatoria}:" + "${lista.duracion};"
            listasData.appendText("${nl}\n")
            println("Lista creada")
        }

        fun getAll(): ArrayList<ListaReproduccion>{
            if (listas.isEmpty()) {
                listasData.readLines().forEach {
                    val gLine = it.split(";")
                    val listLine = gLine[0].split(":")
                    val songsLine = gLine[1].split(":")
                    val lista = ListaReproduccion(
                        listLine[0].toInt(),
                        listLine[1],
                        listLine[2].toBoolean(),
                        listLine[3].toDouble()
                    )
                    songsLine.forEach { s ->
                        if (s != "") {
                            val cancion = Cancion.getById(s.toInt())
                            lista.listaCanciones.add(cancion)
                        }
                    }
                    listas.add(lista)
                }
            }
            numListas = listas.size
            return listas
        }

        fun updateList(lista: ListaReproduccion) {
            val c1 = getAll()
            c1.forEachIndexed { index, list ->
                if (list.id == lista.id) {
                    c1[index] = lista
                    uptLines()
                    println("Lista actualizada")
                    return
                }
            }
        }

        fun uptLines() {
            val nnl = listas.map {
                "${it.id}:" +
                "${it.nombre}:" +
                "${it.reproduccionAleatoria}:"+
                "${calcularDuracion(it)}:"+
                ";"+ appendSongsIndex(it)
            }
            listasData.writeText(nnl.joinToString("\n"))
        }

        fun appendSongsIndex(listaReproduccion: ListaReproduccion):String{
            var aux = ""
            listaReproduccion.listaCanciones.forEachIndexed { index, sg ->
                aux += if (index < listaReproduccion.listaCanciones.size-1){
                    "${sg.id}:"
                }else{
                    "${sg.id}"
                }
            }
            return aux
        }

        fun calcularDuracion(lista: ListaReproduccion): Double{
            var d = 0.0
            lista.listaCanciones.forEach {
                d += it.duracion
            }
            return d
        }

        fun deleteById(id: Int) {
            val removido = getAll().removeIf { lista -> (lista.id == id) }
            if (removido) {
                uptLines()
                println("\nLista de reproduccion eliminada correctamente.")
            } else {
                println("\nLa lista de reproduccion con ID $id no existe.")
            }
        }

        fun getById(id: Int): ListaReproduccion{
            for (item in listas) {
                if (item.id == id) {
                    println("Lista encontrada")
                    return item
                }
            }
            println("No se ha encontrado la lista.")
            return ListaReproduccion()
        }

        fun mostrarListas() {
            val sg = getAll()
            sg.forEach { println(it) }
        }
    }

}