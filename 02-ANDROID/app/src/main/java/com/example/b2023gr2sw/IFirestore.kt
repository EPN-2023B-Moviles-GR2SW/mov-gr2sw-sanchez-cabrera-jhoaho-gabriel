package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class IFirestore : AppCompatActivity() {
    var query: Query? = null
    val arreglo: ArrayList<ICities> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirestore)
        val listView = findViewById<ListView>(R.id.lv_firestore)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonDatosPrueba = findViewById<Button>(R.id.btn_fs_datos_prueba)
        botonDatosPrueba.setOnClickListener { crearDatosPrueba() }

        val botonOrderBy = findViewById<Button>(R.id.btn_fs_order_by)
        botonOrderBy.setOnClickListener { consultarConOrderBy(adaptador) }

        val botonObtenerDocumento = findViewById<Button>(R.id.btn_fs_odoc)
        botonObtenerDocumento.setOnClickListener { consultarDocumento(adaptador) }

        val botonCrear = findViewById<Button>(R.id.btn_fs_crear)
        botonCrear.setOnClickListener { crearEjemplo() }

        val botonFirebaseEliminar = findViewById<Button>(R.id.btn_fs_eliminar)
        botonFirebaseEliminar.setOnClickListener { eliminarRegistro() }

        val botonFirebaseEmpezarPaginar = findViewById<Button>(R.id.btn_fs_epaginar)
        botonFirebaseEmpezarPaginar.setOnClickListener {
            query = null; consultarCiudades(adaptador)
        }

        val botonFirebasePaginar = findViewById<Button>(R.id.btn_fs_paginar)
        botonFirebasePaginar.setOnClickListener { consultarCiudades(adaptador) }

        val botonIndiceCompuesto = findViewById<Button>(R.id.btn_fs_ind_comp)
        botonIndiceCompuesto.setOnClickListener { consultarIndiceCompuesto(adaptador) }
    }

    private fun consultarIndiceCompuesto(adaptador: ArrayAdapter<ICities>) {
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico.whereEqualTo("capital", false)
            .whereLessThanOrEqualTo("population", 4000000)
            .orderBy("population", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                for (ciudad in it){
                    anadirAArregloCiudad(ciudad)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {  }
    }

    private fun consultarCiudades(adaptador: ArrayAdapter<ICities>) {
        val db = Firebase.firestore
        val citiesRef = db.collection("cities").orderBy("population").limit(1)
        var tarea: Task<QuerySnapshot>? = null
        if (query == null){
            tarea = citiesRef.get()
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
        }else{
            tarea = query!!.get()
        }
        if (tarea != null){
            tarea.addOnSuccessListener { documentSnapshot ->
                guardarQuery(documentSnapshot, citiesRef)
                for (ciudad in documentSnapshot){
                    anadirAArregloCiudad(ciudad)
                }
                adaptador.notifyDataSetChanged()
            }
                .addOnFailureListener {  }
        }
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        refCities: Query
    ){
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = refCities
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    private fun eliminarRegistro() {
        val db = Firebase.firestore
        val referenciaEjemploEstudiante = db.collection("ejemplo")

        referenciaEjemploEstudiante.document("12345678").delete()
            .addOnCompleteListener {  }
            .addOnFailureListener {  }
    }

    private fun crearEjemplo() {
        val db = Firebase.firestore
        val referenciaEjemploEstudiante = db.collection("ejemplo")
        val datosEstudiante = hashMapOf(
            "nombre" to "Jhoaho",
            "graduado" to false,
            "promedio" to 14.00,
            "direccion" to hashMapOf(
                "direccion" to "Mitad del mundo",
                "numeroCalle" to 1234
            ),
            "materias" to listOf("web", "moviles")
        )

        referenciaEjemploEstudiante.document("12345678").set(datosEstudiante)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }

        val identificador = Date().time
        referenciaEjemploEstudiante.document(identificador.toString()).set(datosEstudiante)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
        referenciaEjemploEstudiante.add(datosEstudiante)
            .addOnCompleteListener {  }
            .addOnFailureListener {  }
    }

    private fun consultarDocumento(adaptador: ArrayAdapter<ICities>) {
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        // Coleccion "ciudad"
        //     -> Coleccion "barrio"
        //            -> Coleccion "direccion"
        // "Quito" => "La_Floresta" => "E90-001"
        // db.collection("ciudad").document("Quito")
        //   .collection("barrio").document("La Floresta").collection("direccion")
        //   .document("E90-001")
        // .collection("nombre_coleccion_hijo").document("id_hijo")
        // .collection("nombre_coleccion_nieto").document("id_nieto")

        citiesRefUnico.document("BJ").get().addOnSuccessListener {
            arreglo.add(
                ICities(
                    it.data?.get("name") as String?,
                    it.data?.get("state") as String?,
                    it.data?.get("country") as String?,
                    it.data?.get("capital") as Boolean?,
                    it.data?.get("population") as Long?,
                    it.data?.get("regions") as
                            ArrayList<String>?
                )
            )
            adaptador.notifyDataSetChanged()
        }
            .addOnFailureListener {
                //salio mal
            }
    }

    fun consultarConOrderBy(adaptador: ArrayAdapter<ICities>) {
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico.orderBy("population", Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
                for (ciudad in it){
                    ciudad.id
                    anadirAArregloCiudad(ciudad)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {
                //Errores
            }
    }

    fun anadirAArregloCiudad(ciudad: QueryDocumentSnapshot) {
        val nuevaCiudad = ICities(
            ciudad.data.get("name") as String?,
            ciudad.data.get("state") as String?,
            ciudad.data.get("country") as String?,
            ciudad.data.get("capital") as Boolean?,
            ciudad.data.get("population") as Long?,
            ciudad.data.get("regions") as ArrayList<String>?
        )
        arreglo.add(nuevaCiudad)
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

    private fun crearDatosPrueba() {
        val db = Firebase.firestore

        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal"),
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal"),
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast"),
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu"),
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei"),
        )
        cities.document("BJ").set(data5)
    }
}