package com.example.examen_v1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen_v1.R
import com.example.examen_v1.dao.EDatabase
import com.example.examen_v1.model.Cancion
import com.example.examen_v1.model.ListaReproduccion
import com.google.android.material.snackbar.Snackbar

class ViewCrearCancion : AppCompatActivity() {
    var idLista = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_crear_cancion)

        idLista = intent.getIntExtra("idCancion", -1)

        val botonCrearCancion = findViewById<Button>(R.id.btn_cc_guardar)
        botonCrearCancion.setOnClickListener {
            val nombreCancion = findViewById<EditText>(R.id.et_cc_nombre)!!.text.toString()
            val duracionCancion = findViewById<EditText>(R.id.et_cc_duracion)!!.text.toString().toDouble()
            val autorCancion = findViewById<EditText>(R.id.et_cc_autor)!!.text.toString()
            val favCancion = findViewById<Switch>(R.id.sw_cc_fav).isChecked
            val nuevaCancion = EDatabase.database!!.crearCancion(nombreCancion, duracionCancion, favCancion, autorCancion)
            if (nuevaCancion) {
                val idCancion = EDatabase.database!!.getLastSongId()
                EDatabase.database!!.agregarCancionALista(idLista, idCancion)
                mostrarSnackbar("La cancion se ha creado con exito")
            } else mostrarSnackbar("Hubo un error en la creacion")
        }

    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_c_creacion_view), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }
}