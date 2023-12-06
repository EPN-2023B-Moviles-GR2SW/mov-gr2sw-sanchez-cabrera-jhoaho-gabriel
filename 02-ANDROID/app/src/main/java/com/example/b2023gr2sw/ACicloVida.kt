package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.compose.material3.Snackbar
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {

    var textoGlobal = ""

    fun mostrarSnackbar(texto: String) {
        textoGlobal += texto
        val snack =
            Snackbar.make(findViewById(R.id.cl_ciclo_vida), textoGlobal, Snackbar.LENGTH_LONG)
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("Hola")
        mostrarSnackbar("onCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            //Guardar variables
            //primitivos
            putString("textoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //RECUPERAR LAS VARIABLES
        //PRIMITIVOS
        val textoRecuperado: String? = savedInstanceState.getString("textoGuardado")
        if (textoRecuperado != null) {
            mostrarSnackbar(textoRecuperado)
            textoGlobal = textoRecuperado
        }
    }


}