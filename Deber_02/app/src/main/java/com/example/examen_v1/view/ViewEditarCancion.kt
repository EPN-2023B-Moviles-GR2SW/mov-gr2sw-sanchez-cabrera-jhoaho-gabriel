package com.example.examen_v1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen_v1.R
import com.example.examen_v1.dao.EDatabase
import com.example.examen_v1.model.Cancion
import com.google.android.material.snackbar.Snackbar

class ViewEditarCancion : AppCompatActivity() {

    var idCancion = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_editar_cancion)

        idCancion = intent.getIntExtra("idCancion", -1)

        val botonGuardarCancion = findViewById<Button>(R.id.btn_ec_guardar)
        botonGuardarCancion.setOnClickListener {
            val nombreCancion = findViewById<EditText>(R.id.et_ec_nombre)!!.text.toString()
            val duracionCancion = findViewById<EditText>(R.id.et_ec_duracion)!!.text.toString().toDouble()
            val autorCancion = findViewById<EditText>(R.id.et_ec_autor)!!.text.toString()
            val favoritaCancion = findViewById<Switch>(R.id.sw_ec_fav).isChecked
            val act = EDatabase.database!!.actualizarCancion(idCancion, nombreCancion, duracionCancion, favoritaCancion, autorCancion)
            if (act) {
                mostrarSnackbar("Cancion actualizada con exito")
            }else{
                mostrarSnackbar("Ha ocurrido un error")
            }
        }

        llenarCampos()
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.id_c_editar), // view
                texto, // texto
                Snackbar.LENGTH_LONG // tiempo
            )
            .show()
    }

    private fun llenarCampos() {
        val nombreCancion = findViewById<EditText>(R.id.et_ec_nombre)
        val duracionCancion = findViewById<EditText>(R.id.et_ec_duracion)
        val autorCancion = findViewById<EditText>(R.id.et_ec_autor)
        val favoritaCancion = findViewById<Switch>(R.id.sw_ec_fav)

        if (idCancion != -1) {
            val cancion = EDatabase.database!!.consultarCancionPorID(idCancion)
            if (cancion !== null) {
                nombreCancion.setText(cancion.getNombre())
                duracionCancion.setText(cancion.getDuracion().toString())
                autorCancion.setText(cancion.getAutor())
                favoritaCancion.isChecked = cancion.isFavorita()
            }
        }
    }
}