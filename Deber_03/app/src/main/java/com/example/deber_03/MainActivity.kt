package com.example.deber_03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)

        val botonShowMessages = findViewById<Button>(R.id.btn_show_messages)
        botonShowMessages.setOnClickListener {
            irActividad(RecyclerViewMessages::class.java)
        }

        val botonShowSearch = findViewById<Button>(R.id.btn_show_search)
        botonShowSearch.setOnClickListener {
            irActividad(RecyclerViewSearch::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}