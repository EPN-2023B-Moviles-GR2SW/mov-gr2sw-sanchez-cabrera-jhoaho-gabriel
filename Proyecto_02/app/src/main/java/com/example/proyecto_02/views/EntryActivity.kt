package com.example.proyecto_02.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.proyecto_02.R
import com.example.proyecto_02.adapter.EntriesProvider
import com.example.proyecto_02.entities.Entrada

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val idEntry = "00000000003" //intent.getStringExtra("idLista")

        var entrada:Entrada? = null

        for (entry in EntriesProvider.entriesList){
            if (entry.id.equals(idEntry)){
                entrada = entry
            }
        }

        val fecha = findViewById<TextView>(R.id.ea_date)
        val hora = findViewById<TextView>(R.id.ea_time)
        val titulo = findViewById<TextView>(R.id.ea_title)
        val desc = findViewById<TextView>(R.id.ea_desc)

        fecha.text = entrada!!.fecha
        hora.text = entrada!!.hora
        titulo.text = entrada!!.titulo
        desc.text = entrada!!.contenido

    }
}