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

class ViewEditarLista : AppCompatActivity() {

    private var idLista = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_editar_lista)

        idLista = intent.getIntExtra("idLista", -1)

        val botonGuardarCambios = findViewById<Button>(R.id.btn_el_guardar)
        botonGuardarCambios.setOnClickListener {
            val nombreLista = findViewById<EditText>(R.id.et_el_nombre)!!.text.toString()
            val reproduccionLista = findViewById<Switch>(R.id.sw_el_ra).isChecked
            val act = EDatabase.database!!.actualizarLista(idLista, nombreLista, reproduccionLista, null)
            if (act){
                mostrarSnackbar("Se ha actualizado la lista de reproduccion")
            }else{
                mostrarSnackbar("Ha ocurrido un error")
            }
        }

        llenarCampos()
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_l_editar), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }

    private fun llenarCampos() {
        val txtNombre = findViewById<EditText>(R.id.et_el_nombre)
        val SwReproduccion = findViewById<Switch>(R.id.sw_el_ra)

        if (idLista != -1) {
            val lista = EDatabase.database!!.consultarListaPorID(idLista)
            if (lista !== null) {
                txtNombre.setText(lista.getNombre())
                SwReproduccion.isChecked = lista.isAleatoria()
            }
        }
    }
}