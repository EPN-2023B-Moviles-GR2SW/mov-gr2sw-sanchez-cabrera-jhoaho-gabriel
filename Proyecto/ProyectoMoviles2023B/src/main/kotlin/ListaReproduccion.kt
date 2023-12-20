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

    fun agregarCancion(cancion: Cancion) { //edit p1
        this.listaCanciones.add(cancion)
    }

    fun quitarCancionPorId(idCancion: Int) { //edit p2
        val cancion = Cancion.getById(idCancion)
        this.listaCanciones.remove(cancion)
    }

    fun cambiarNombre(nombre: String) {
        this.nombre = nombre
    }

    fun reproduccionAleatoria(value: Boolean) {
        this.reproduccionAleatoria = value
    }

    fun mostrarCanciones(){
        listaCanciones.forEach{ println(it) }
    }

    fun cargarCanciones() { //ingresar array o archivo
        TODO()
    }

    override fun toString(): String {
        return "ListaReproduccion(id=$id, nombre='$nombre', reproduccionAleatoria=$reproduccionAleatoria, duracion=$duracion, listaCanciones=$listaCanciones)"
    }

    companion object {
        var numListas: Int = 0
        var listas:ArrayList<ListaReproduccion> = arrayListOf()

        fun crearLista(nombre: String, reproduccionAleatoria: Boolean?, duracion: Double?) { //creacion directa
            listas.add(ListaReproduccion(nombre, reproduccionAleatoria, duracion))
        }

        fun borrarLista(lista: ListaReproduccion) {
            if(listas.contains(lista)){
                listas.remove(lista)
                println("Lista de reproduccion eliminada")
            }else{
                println("Error: La lista de reproduccion no existe")
            }
        }

        fun getById(id: Int): ListaReproduccion{
            for (item in listas){
                if (item.id == id){
                    return item
                }
            }
            return ListaReproduccion()
        }
    }

}