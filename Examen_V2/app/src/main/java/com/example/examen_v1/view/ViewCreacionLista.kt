package com.example.examen_v1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen_v1.R
import com.example.examen_v1.dao.EDatabase
import com.example.examen_v1.model.ListaReproduccion
import com.google.android.material.snackbar.Snackbar

class ViewCreacionLista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_creacion_lista)

        val botonCrearLista = findViewById<Button>(R.id.btn_create_ls)
        botonCrearLista.setOnClickListener {
            val nombreLista = findViewById<EditText>(R.id.input_name_ls)!!.text.toString()
            val reproduccionAleatoria = findViewById<Switch>(R.id.input_active_ls)!!.isChecked
            val nuevaLista = EDatabase.database!!.crearLista(nombreLista, reproduccionAleatoria, 0.0)
            if (nuevaLista.equals("true")) mostrarSnackbar("Lista creada exitosamente") else mostrarSnackbar(
                nuevaLista
            )
        }
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_l_creacion_view), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
}