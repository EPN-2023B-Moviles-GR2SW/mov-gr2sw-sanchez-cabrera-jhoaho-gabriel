package com.example.examen_v1.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examen_v1.model.Cancion
import com.example.examen_v1.model.ListaReproduccion

class ESqliteHelper(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "deber_02",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCreateTableCancion = """
            CREATE TABLE CANCION(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            duracion DOUBLE,
            favorita BOOLEAN,
            autor VARCHAR(30)
            );
        """.trimIndent()
        val scriptSQLCreateTableLista = """
            CREATE TABLE LISTA(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            reproduccionAleatoria BOOLEAN,
            duracion DOUBLE
            );
        """.trimIndent()
        val scriptSQLCreateTableInterseccion = """
            CREATE TABLE LISTAXCANCION(
            idLista INTEGER,
            idCancion INTEGER,
            FOREIGN KEY(idLista) REFERENCES LISTA(id),
            FOREIGN KEY(idCancion) REFERENCES CANCION(id)
            );
        """.trimIndent()
        db!!.execSQL(scriptSQLCreateTableCancion)
        db!!.execSQL(scriptSQLCreateTableLista)
        db!!.execSQL(scriptSQLCreateTableInterseccion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun crearCancion(nombre: String, duracion: Double, favorita: Boolean, autor: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("duracion", duracion)
        valoresAGuardar.put("favorita", favorita)
        valoresAGuardar.put("autor", autor)
        val resultadoGuardar = baseDatosEscritura.insert(
            "CANCION",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun actualizarCancion(
        id: Int,
        nombre: String,
        duracion: Double,
        favorita: Boolean,
        autor: String
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("duracion", duracion)
        valoresAActualizar.put("favorita", favorita)
        valoresAActualizar.put("autor", autor)
        val parametroConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizar = baseDatosEscritura.update(
            "CANCION",
            valoresAActualizar,
            "id=?",
            parametroConsultaActualizar
        )
        baseDatosEscritura.close()
        return resultadoActualizar.toInt() != -1
    }

    fun getAllCancion(): ArrayList<Cancion> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CANCION
        """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            null
        )
        val existenCanciones = resultadoConsultaLectura.moveToFirst()
        val arreglo = arrayListOf<Cancion>()
        if (existenCanciones) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val duracion = resultadoConsultaLectura.getDouble(2)
                val favorita = resultadoConsultaLectura.getInt(3) > 0
                val autor = resultadoConsultaLectura.getString(4)
                if (id != null) {
                    arreglo.add(Cancion(id, nombre, duracion, favorita, autor))
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }

    fun consultarCancionPorID(id: Int): Cancion {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CANCION WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        val existeCancion = resultadoConsultaLectura.moveToFirst()
        val cancionEncontrada = Cancion(0, "", 0.0, false, "")
        if (existeCancion) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val duracion = resultadoConsultaLectura.getDouble(2)
                val favorita = resultadoConsultaLectura.getInt(3) > 0
                val autor = resultadoConsultaLectura.getString(4)
                if (id != null) {
                    // llenar el arreglo con un nuevo BEntrenador
                    cancionEncontrada.setId(id)
                    cancionEncontrada.setNombre(nombre)
                    cancionEncontrada.setDuracion(duracion)
                    cancionEncontrada.setFavorita(favorita)
                    cancionEncontrada.setAutor(autor)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return cancionEncontrada
    }

    fun eliminarCancionPorID(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "LISTAXCANCION", // Nombre tabla
                "idCancion=?", // Consulta Where
                parametrosConsultaDelete
            )
        val resultadoEliminacion2 = conexionEscritura.delete(
            "CANCION",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return !(resultadoEliminacion.toInt() == -1 && resultadoEliminacion2.toInt() == -1)
    }

    fun crearLista(nombre: String, reproduccionAleatoria: Boolean?, duracion: Double?): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("reproduccionAleatoria", reproduccionAleatoria)
        valoresAGuardar.put("duracion", duracion)
        val resultadoGuardar = baseDatosEscritura.insert(
            "LISTA",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun actualizarLista(
        id: Int,
        nombre: String,
        reproduccionAleatoria: Boolean?,
        duracion: Double?
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("reproduccionAleatoria", reproduccionAleatoria)
        if (duracion != null) {
            valoresAActualizar.put("duracion", duracion)
        }
        val parametroConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizar = baseDatosEscritura.update(
            "LISTA",
            valoresAActualizar,
            "id=?",
            parametroConsultaActualizar
        )
        baseDatosEscritura.close()
        return resultadoActualizar.toInt() != -1
    }

    fun getAllListas(): ArrayList<ListaReproduccion> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM LISTA
        """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            null
        )
        val existenListas = resultadoConsultaLectura.moveToFirst()
        val arreglo = arrayListOf<ListaReproduccion>()
        if (existenListas) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val reproduccionAleatoria = resultadoConsultaLectura.getInt(2) > 0
                val duracion = resultadoConsultaLectura.getDouble(3)
                if (id != null) {
                    val lss = ListaReproduccion(id, nombre, reproduccionAleatoria, duracion, null)
                    val sl = this.getAllCancionesPorLista(id)
                    lss.setListaDeCanciones(sl)
                    arreglo.add(lss)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }

    fun getAllCancionesPorLista(idLista: Int): ArrayList<Cancion> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT c.id, c.nombre, c.duracion, c.favorita, c.autor from (
            SELECT * FROM LISTAXCANCION WHERE idLista = ?
            ) as it join CANCION as c on it.idCancion = c.id
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idLista.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        val existenCanciones = resultadoConsultaLectura.moveToFirst()
        val arreglo = arrayListOf<Cancion>()
        if (existenCanciones) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val duracion = resultadoConsultaLectura.getDouble(2)
                val favorita = resultadoConsultaLectura.getInt(3) > 0
                val autor = resultadoConsultaLectura.getString(4)
                if (id != null) {
                    arreglo.add(Cancion(id, nombre, duracion, favorita, autor))
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arreglo
    }

    fun consultarListaPorID(id: Int): ListaReproduccion {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM LISTA WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        val existeLista = resultadoConsultaLectura.moveToFirst()
        val listaEncontrada = ListaReproduccion(0, "", false, 0.0, null)
        if (existeLista) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val reproduccionAleatoria = resultadoConsultaLectura.getInt(2) > 0
                val duracion = resultadoConsultaLectura.getDouble(3)
                if (id != null) {
                    // llenar el arreglo con un nuevo BEntrenador
                    listaEncontrada.setId(id)
                    listaEncontrada.setNombre(nombre)
                    listaEncontrada.setDuracion(duracion)
                    listaEncontrada.setReproduccionAleatoria(reproduccionAleatoria)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaEncontrada
    }

    fun eliminarListaPorID(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "LISTAXCANCION", // Nombre tabla
                "idLista=?", // Consulta Where
                parametrosConsultaDelete
            )
        val resultadoEliminacion2 = conexionEscritura.delete(
            "LISTA",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return !(resultadoEliminacion.toInt() == -1 && resultadoEliminacion2.toInt() == -1)
    }

    fun agregarCancionALista(idLista: Int, idCancion: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("idLista", idLista)
        valoresAGuardar.put("idCancion", idCancion)
        val resultadoGuardar = baseDatosEscritura.insert(
            "LISTAXCANCION",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun quitarCancionDeLista(idLista: Int, idCancion: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idLista.toString(), idCancion.toString())
        val resultadoGuardar = baseDatosEscritura.delete(
            "LISTAXCANCION",
            "idLista=? AND idCancion=?",
            parametrosConsultaDelete
        )
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun getLastSongId(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CANCION ORDER BY ID DESC LIMIT 1
        """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            null
        )

        val existeCancion = resultadoConsultaLectura.moveToFirst()
        var idCancion = -1
        if (existeCancion) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                if (id != null) {
                    // llenar el arreglo con un nuevo BEntrenador
                    idCancion = id
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return idCancion
    }
}