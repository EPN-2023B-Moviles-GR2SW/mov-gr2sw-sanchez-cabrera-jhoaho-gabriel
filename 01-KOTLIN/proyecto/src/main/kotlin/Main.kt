import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")
    //variabels inmutables o constantes
    val inmutable: String = "Adrian"
    //variables mutables o varibales
    var mutable: String = "Vicente"
    mutable = "Jhoaho"

    //var > val
    //Duck typing
    var ejemploVariable = "Jhoaho Sanchez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()


    //Variables primitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true;
    //Clases de java
    val fechaNacimiento: Date = Date()

    // switch
    val estadoCivilWhen = 'C'
    when (estadoCivilWhen) {
        ('C') -> {
            println("Casado")
        }

        ('S') -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }

    }

    val esSoltero = (estadoCivilWhen == 'S')
    val coqueteo = if (esSoltero) "si" else "no"

    calcularSueldo(10.0)
    calcularSueldo(10.0, 15.0)
    calcularSueldo(10.0, 15.0, 20.0)
    calcularSueldo(10.0, bonoEspecial = 20.0) //parametros nombrados

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglo estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglo dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)



    //For each -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach{
        valorActual: Int -> println("Valor actual: ${valorActual}")
    }
    //it significa el elemento iterado
    arregloDinamico.forEach{ println("Valor actual: ${it}") }

    arregloEstatico.forEachIndexed { indice:Int, valorActual:Int  ->
        println("Valor: ${valorActual}, Indice: ${indice}")
    }
    println(respuestaForEach)


    //Map -> Muta el arreglo (cambia el arreglo)
    //1) Enviemos el nuevo valor de la iteracion
    //2) Nos devuelven un nuevo arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico.map {
        valorActual: Int -> return@map valorActual.toDouble() + 100.0
    }

    println(respuestaMap)
    val respuestaMap2 = arregloDinamico.map { it + 15 }

    //Filter -> Filtrar el arreglo
    //1) Devolver una expresion (True o False)
    //2) Nuevo arreglo filtrado

    val respuestaFilter: List<Int> = arregloDinamico.filter {
        valorActual:Int ->
        val mayoresACinco:Boolean = valorActual>5
        return@filter mayoresACinco
    }

    val respuestaFiltrDos = arregloDinamico.filter {
        it <= 5
    }

    println(respuestaFilter)
    println(respuestaFiltrDos)

}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Inicializando")
    }

}

abstract class Numeros( //construcctor primario
    protected val numeroUno: Int,
    protected val numeroDos: Int
) {
    init {
        this.numeroUno; this.numeroDos;
        println("Inicializando")
    }
}

class Suma( //Constructor primario numeros
    unoParametro: Int, //parametro
    dosParametro: Int //parametro
) : Numeros(unoParametro, dosParametro) { //Extendiendo y mandando los paramteros al super
    init { //Bloque de codigo constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor( //segundo constructor
        uno: Int?,
        dos: Int
    ) : this(
        if (uno == null) 0 else uno, dos
    )

    constructor( //Tercer constructor
        uno: Int,
        dos: Int?
    ) : this(
        uno,
        if (dos == null) 0 else dos
    )

    constructor( //Cuarto constructor
        uno: Int?, //parametros
        dos: Int? //parametros
    ) : this( //llamada al constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    //public por defecto, o usar private/protected
    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        //Suma.agregarHistorial(total)
        agregarHistorial(total)
        return total
    }

    companion object { //Atributos y metodos compratidos
        //Entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
        }


    }

}


//Void -> Unit

fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")//template string
}

fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double = 12.0, //Opcional(defecto)
    bonoEspecial: Double? = null, //Opcion null -> nullable
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) + bonoEspecial;
    }
}


