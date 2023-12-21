fun main(args: Array<String>) {

    //Menu

    Cancion.getAll()
    ListaReproduccion.getAll()
    var opt = 0

    do {
        println("\tMenu\nIngrese el numero de la operacion a realizar:")
        println("1. Crear una Lista de reproduccion.")
        println("2. Crear una Cancion.")
        println("3. Ver todas las listas de reproduccion.")
        println("4. Ver lista de reproduccion por ID.")
        println("5. Ver todas las canciones.")
        println("6. Ver cancion por ID.")
        println("7. Actualizar datos de Lista de reproduccion.")
        println("8. Actualizar datos de cancion.")
        println("9. Agregar/Quitar cancion de lista de reproduccion.")
        println("10. Borrar lista de reproduccion.")
        println("11. Borrar cancion.")
        println("12. Salir.")

        opt = readln().toInt()

        when(opt){
            1 -> {
                println("Ingrese el nombre de la lista:")
                val n = readln()
                ListaReproduccion.crearLista(n, false, 0.0)
            }
            2 -> {
                println("\nIngrese el nombre de la cancion: ")
                val n = readln()
                println("Ingrese la duracion de la cancion: ")
                val d = readln().toDouble()
                println("Ingrese el autor de la cancion: ")
                val a = readln()
                Cancion.crearCancion(n, d, false, a)
            }
            3 -> {
                println("***\tListas encontradas\t***")
                ListaReproduccion.mostrarListas()
            }
            4 -> {
                println("\nIngrese el ID de la lista de reproduccion a mostrar: ")
                val id = readln().toInt()
                println(ListaReproduccion.getById(id))
                println()
            }
            5 -> {
                println("***\tCanciones encontradas\t***")
                Cancion.mostrarCanciones()
            }
            6 -> {
                println("\nIngrese el ID de la cancion a mostrar: ")
                val id = readln().toInt()
                println(Cancion.getById(id))
                println()
            }
            7 -> {
                println("\nIngrese el ID de la lista de reproduccion:")
                val id = readln().toInt()
                var list = ListaReproduccion.getById(id)
                println("Escoja:\n1. Cambiar nombre\n2. Cambiar reproduccion aleatoria")
                val op = readln().toInt()
                if (op == 1){
                    println("Ingrese el nuevo nombre de la lista: ")
                    val nombre = readln()
                    list.cambiarNombre(nombre)
                }else{
                    list.reproduccionAleatoria(!list.isAleatoria())
                }
            }
            8 -> {
                println("\nIngrese el ID de la cancion:")
                val id = readln().toInt()
                var cancion = Cancion.getById(id)
                println("Escoja:\n1. Cambiar nombre\n2. Cambiar favorito")
                val op = readln().toInt()
                if (op == 1){
                    println("Ingrese el nuevo nombre de la cancion: ")
                    val nombre = readln()
                    cancion.actualizarNombre(nombre)
                }else{
                    cancion.actualizarFavorita(!cancion.favorita)
                }
            }
            9 -> {
                println("\nIngrese el ID de la lista:")
                val id = readln().toInt()
                var list = ListaReproduccion.getById(id)
                println("Escoja:\n1. Agregar cancion\n2. Quitar cancion")
                val op = readln().toInt()
                println("Ingrese el ID de la cancion: ")
                val idCancion = readln().toInt()
                if (op == 1){
                    list.agregarCancion(idCancion)
                }else{
                    list.quitarCancionPorId(idCancion)
                }
            }
            10 -> {
                println("\nIngrese el ID de la lista:")
                val id = readln().toInt()
                ListaReproduccion.deleteById(id)
            }
            11 ->{
                println("\nIngrese el ID de la cancion:")
                val id = readln().toInt()
                Cancion.deleteById(id)
            }
            12 -> {
                println("Saliendo...")
            }
            else ->{
                println("Error: Opcion no reconocida")
            }
        }
    }while (opt!=12)
}