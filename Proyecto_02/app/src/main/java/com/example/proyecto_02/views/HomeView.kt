package com.example.proyecto_02.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_02.R
import com.example.proyecto_02.adapter.EntriesAdapter
import com.example.proyecto_02.adapter.EntriesProvider
import com.example.proyecto_02.adapter.EspaciadoItemDecoration
import com.google.firebase.auth.FirebaseAuth

class HomeView : AppCompatActivity() {

    var idItemSeleccionado = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_view)
        initRecylcerView()

        val userName = findViewById<TextView>(R.id.HV_username_tv)
        userName.text = FirebaseAuth.getInstance().currentUser?.displayName
        userName.setOnClickListener {
            irActividad(EntryActivity::class.java)
        }

        val btonEntry = findViewById<Button>(R.id.btn_entries).setOnClickListener {
            irActividad(EntryActivity::class.java)
        }

    }


    private fun initRecylcerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.HV_rv_entries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EntriesAdapter(EntriesProvider.entriesList)

        // Configurar el adaptador y asignar al RecyclerView

        val espacioVertical = resources.getDimensionPixelSize(R.dimen.espacio_vertical)
        val espacioHorizontal = resources.getDimensionPixelSize(R.dimen.espacio_horizontal)

        val itemDecoration = EspaciadoItemDecoration(espacioVertical, espacioHorizontal)
        recyclerView.addItemDecoration(itemDecoration)
        registerForContextMenu(recyclerView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_entradas, menu);
        val infoLista = menuInfo as AdapterView.AdapterContextMenuInfo;
        val idLista = infoLista.position
        if(idLista != null){
            idItemSeleccionado = EntriesProvider.entriesList[idLista].id
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mc_open -> {
                try {
                    val idLista = idItemSeleccionado
                    irActividad(EntryActivity::class.java, idLista)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.mc_edit-> {
                try {
                    val idLista = idItemSeleccionado;
                    irActividad(EntryActivity::class.java, idLista)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun irActividad(clase: Class<*>, id:String){
        val intent = Intent(this, clase)
        intent.putExtra("idLista",id)
        startActivity(intent)
    }

    private fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}