package com.example.examen_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.examen_v1.model.ListaReproduccion
import com.example.examen_v1.view.*

class MainActivity : AppCompatActivity() {

    private val listas = ListaReproduccion.listas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCrearLista = findViewById<Button>(R.id.btn_crear_lista)
        botonCrearLista.setOnClickListener {
            irActividad(ViewCreacionLista::class.java)
        }
        updateViewLista()
    }

    private fun updateViewLista() {
        val listView = findViewById<ListView>(R.id.lv_listas_reproduccion)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listas.mapIndexed { index, listaReproduccion ->
                "${index}| ${listaReproduccion.getNombre()}"
            }
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        //registerForContextMenu(listViewLibros)
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        updateViewLista()
    }

    override fun onResume() {
        super.onResume()
        updateViewLista()
    }
}