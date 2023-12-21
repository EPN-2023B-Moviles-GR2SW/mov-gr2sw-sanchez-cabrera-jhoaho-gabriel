import java.io.File

class Cancion {
    var id: Int = 0
    var nombre: String = ""
    var duracion: Double = 0.0
    var favorita: Boolean = false
    var autor: String = ""

    constructor() {
        this.id = numCanciones++
    }

    constructor(id: Int, nombre: String, duracion: Double, favorita: Boolean, autor: String) { //lectura archivo
        this.id = id
        this.nombre = nombre
        this.duracion = duracion
        this.favorita = favorita
        this.autor = autor
    }

    constructor(nombre: String, duracion: Double, favorita: Boolean, autor: String) { //creacion directa
        this.id = numCanciones++
        this.nombre = nombre
        this.duracion = duracion
        this.favorita = favorita
        this.autor = autor
    }

    fun actualizarNombre(nombre: String) {
        this.nombre = nombre
        Cancion.update(this)
    }

    fun actualizarFavorita(favorita: Boolean) {
        this.favorita = favorita
        Cancion.update(this)
    }

    override fun toString(): String {
        return "Cancion(id=$id, nombre='$nombre', duracion=$duracion, favorita=$favorita, autor='$autor')"
    }


    companion object {
        var numCanciones: Int = 0
        var canciones: ArrayList<Cancion> = arrayListOf()
        val cancionesData: File =
            File("src/main/kotlin/data/canciones.txt").apply { takeIf { !exists() }?.run { createNewFile() } }

        fun crearCancion(nombre: String, duracion: Double, favorita: Boolean, autor: String) {
            val c: Cancion = Cancion(nombre, duracion, favorita, autor)
            canciones.add(c)
            val nl: String = "${c.id}:" + "${c.nombre}:" + "${c.duracion}:" + "${c.favorita}:" + "${c.autor}"
            cancionesData.appendText("${nl}\n")
            println("Cancion creada")
        }

        fun getAll(): ArrayList<Cancion> {
            if (canciones.isEmpty()) {
                cancionesData.readLines().forEach {
                    val line = it.split(":")
                    val cancion = Cancion(
                        line[0].toInt(),
                        line[1],
                        line[2].toDouble(),
                        line[3].toBoolean(),
                        line[4]
                    )
                    canciones.add(cancion)
                }
            }
            numCanciones = canciones.size
            return canciones
        }

        fun update(cancion: Cancion) {
            val c1 = getAll()
            c1.forEachIndexed { index, song ->
                if (song.id == cancion.id) {
                    c1[index] = cancion
                    uptLines()
                    println("Cancion actualizada")
                    return
                }
            }
        }

        fun uptLines() {
            val nnl = canciones.map {
                "${it.id}:" +
                        "${it.nombre}:" +
                        "${it.duracion}:" +
                        "${it.favorita}:" +
                        "${it.autor}"
            }
            cancionesData.writeText(nnl.joinToString("\n"))
        }

        fun deleteById(id: Int) {
            val cancion = getById(id)
            val removido = getAll().removeIf { cancion -> (cancion.id == id) }
            if (removido) {
                ListaReproduccion.listas.forEach {
                    if (it.getListaCanciones().contains(cancion)) {
                        it.quitarCancionPorId(id)
                    }
                }
                uptLines()
                println("\nCancion eliminado correctamente.")
            } else {
                println("\nLa cancion con ID $id no existe.")
            }
        }

        fun getById(id: Int): Cancion {
            for (item in canciones) {
                if (item.id == id) {
                    println("Cancion encontrada")
                    return item
                }
            }
            println("No se ha encontrado la cancion.")
            return Cancion()
        }

        fun mostrarCanciones() {
            val sg = getAll()
            sg.forEach { println(it) }
        }

    }


}