package com.example.examen_v1.dao

import com.example.examen_v1.model.Cancion
import com.example.examen_v1.model.ListaReproduccion
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList
import java.util.Date

class FirebaseHelper {
    var queryLista: Query? = null
    var queryCancion: Query? = null
    var arregloListas: ArrayList<ListaReproduccion> = arrayListOf()
    var arregloCanciones: ArrayList<Cancion> = arrayListOf()


    fun crearCancion(
        nombre: String,
        duracion: Double,
        favorita: Boolean,
        autor: String,
        idLista: Int
    ): Boolean { //Done!

        var msg = false
        val db = Firebase.firestore
        val cancionRef = db.collection("ListaDeReproduccion/${idLista}/Canciones")
        val datosCancion = hashMapOf(
            "nombre" to nombre,
            "duracion" to duracion,
            "favorita" to favorita,
            "autor" to autor
        )
        val identificador = Date().time
        cancionRef // (crear/actualizar)
            .document(identificador.toString())
            .set(datosCancion)
            .addOnSuccessListener {
                msg = true
            }
            .addOnFailureListener {
                msg = false
            }

        return msg
    }

    fun actualizarCancion(
        id: Int,
        nombre: String,
        duracion: Double,
        favorita: Boolean,
        autor: String,
        idLista: Int
    ): Boolean { //Done!
        var msg = false
        val db = Firebase.firestore
        val cancionRef = db.collection("ListaDeReproduccion/${idLista}/Canciones")

        val datosCancion = hashMapOf(
            "nombre" to nombre,
            "duracion" to duracion,
            "favorita" to favorita,
            "autor" to autor
        )

        cancionRef
            .document(id.toString())
            .update(datosCancion as Map<String, Any>)
            .addOnSuccessListener {
                msg = true
            }
            .addOnFailureListener {
                msg = false
            }

        return msg
    }

    fun consultarCancionPorID(id: Int, idLista: Int): Cancion? { //Done!
        var cancion: Cancion? = null
        val db = Firebase.firestore
        val departamentosRef = db.collection("ListaDeReproduccion/${idLista}/Canciones")

        departamentosRef
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                cancion = Cancion(
                    (it.id).toInt(),
                    it.data?.get("nombre") as String,
                    it.data?.get("duracion") as Double,
                    it.data?.get("favorita") as Boolean,
                    it.data?.get("autor") as String
                )
            }
            .addOnFailureListener {
                // salio Mal
            }
        return cancion
    }

    fun eliminarCancionPorID(id: Int, idLista: Int): Boolean { //Done!

        var msg = false
        val db = Firebase.firestore
        val cancionRef = db.collection("ListaDeReproduccion/${idLista}/Canciones")

        cancionRef
            .document(id.toString())
            .delete() // elimina
            .addOnCompleteListener {
                msg = true
            }
            .addOnFailureListener {
                msg = false
            }
        return msg
    }

    fun crearLista(
        nombre: String,
        reproduccionAleatoria: Boolean?,
        duracion: Double
    ): String { //Done!
        var rs = ""
        val db = Firebase.firestore
        val listaRef = db.collection("ListaDeReproduccion")
        val datosLista = hashMapOf(
            "nombre" to nombre,
            "duracion" to duracion,
            "reproduccionAleatoria" to reproduccionAleatoria
        )

        val identificador = Date().time
        listaRef.document(identificador.toString()).set(datosLista)
            .addOnSuccessListener {
                rs = "true"
            }
            .addOnFailureListener {
                rs = it.toString()

            }
        return rs
    }

    fun actualizarLista( //Done!
        id: Int,
        nombre: String,
        reproduccionAleatoria: Boolean?,
        duracion: Double?
    ): Boolean {

        val db = Firebase.firestore
        val listaRef = db.collection("ListaDeReproduccion")
        var msg = false
        val datosLista = hashMapOf(
            "nombre" to nombre,
            "duracion" to duracion,
            "reproduccionAleatoria" to reproduccionAleatoria
        )

        listaRef.document(id.toString()).update(datosLista as Map<String, Any>)
            .addOnSuccessListener {
                msg = true
            }
            .addOnFailureListener {
                msg = false
            }

        return msg
    }

    fun getAllListas(): ArrayList<ListaReproduccion> { //Done!

        val db = Firebase.firestore
        val listasRef = db.collection("ListaDeReproduccion")
        var tarea: Task<QuerySnapshot>? = null
        tarea = listasRef.get() // 1era vez
        arregloListas.clear()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQueryLista(documentSnapshots, listasRef)
                    for (lista in documentSnapshots) {
                        anadirLista(lista)
                    }
                }
                .addOnFailureListener {
                }
        }

        return arregloListas
    }

    fun guardarQueryLista(
        documentSnapshots: QuerySnapshot,
        ref: Query
    ) { //other methods
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            queryLista = ref
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun anadirLista(
        document: QueryDocumentSnapshot
    ) { //other methods
        val listaReproduccion = ListaReproduccion(
            document.id.toInt(),
            document.data["nombre"] as String,
            document.data["reproduccionAleatoria"] as Boolean,
            document.data["duracion"] as Double,
            null
        )
        arregloListas.add(listaReproduccion)
    }

    fun guardarQueryCancion(
        documentSnapshots: QuerySnapshot,
        ref: Query
    ) { //other methods
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            queryCancion = ref
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun anadirCancionALista(
        document: QueryDocumentSnapshot
    ) { //other methods review!!!!!!
        val cancion = Cancion(
            document.id.toInt(),
            document.data.get("nombre") as String,
            document.data.get("duracion") as Double,
            document.data.get("favorita") as Boolean,
            document.data.get("autor") as String
        )
        arregloCanciones.add(cancion)
    }

    fun getAllCancionesPorLista(idLista: Int): ArrayList<Cancion> { //Done!

        val db = Firebase.firestore
        val cancionRef = db.collection("ListaDeReproduccion/${idLista}/Canciones")
        var tarea: Task<QuerySnapshot>? = null
        tarea = cancionRef.get() // 1era vez
        arregloCanciones.clear()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQueryCancion(documentSnapshots, cancionRef)
                    for (departamento in documentSnapshots) {
                        anadirCancionALista(departamento)
                    }
                }
                .addOnFailureListener { }
        }

        return arregloCanciones
    }

    fun consultarListaPorID(id: Int): ListaReproduccion { //Done!

        val db = Firebase.firestore
        val listaRef = db.collection("ListaDeReproduccion")
        var lista = ListaReproduccion(null, "", null, null, null)

        listaRef.document(id.toString()).get().addOnSuccessListener {
            lista = ListaReproduccion(
                (it.id as String?)!!.toInt(),
                it.data?.get("nombre") as String,
                it.data?.get("reproduccionAleatoria") as Boolean,
                it.data?.get("duracion") as Double,
                null
            )
        }
            .addOnFailureListener {
                //do something
            }
        return lista
    }

    fun eliminarListaPorID(id: Int): Boolean { //Done!

        var rs = false
        val db = Firebase.firestore
        val listaRef = db.collection("ListaDeReproduccion")

        listaRef
            .document(id.toString())
            .delete() // elimina
            .addOnCompleteListener {
                rs = true
            }
            .addOnFailureListener {
                rs = false
            }

        return rs
    }

}