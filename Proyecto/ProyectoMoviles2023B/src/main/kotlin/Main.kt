fun main(args: Array<String>) {
    //Objetivo: Lista de reproduccion - canciones


    //agregar escritura y lecutra de archivos (json/csv/xml)



    //Menu

    Cancion.getAll()
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
                //crear lista
            }
            2 -> {
                //crear cancion
                println("\nIngrese el nombre de la cancion: ")
                val n = readln()
                println("Ingrese la duracion de la cancion: ")
                val d = readln().toDouble()
                println("Ingrese el autor de la cancion: ")
                val a = readln()
                Cancion.crearCancion(n, d, false, a)
            }
            3 -> {
                // mostrar todas las listas
            }
            4 -> {
                // mostrar una lista por id
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
                //ACtualizar lista
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
                // agregar quitar cancion
            }
            10 -> {
                //borrar lista
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