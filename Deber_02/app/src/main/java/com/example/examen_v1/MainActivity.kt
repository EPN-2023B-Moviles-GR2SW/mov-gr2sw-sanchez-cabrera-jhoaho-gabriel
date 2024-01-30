package com.example.examen_v1

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.examen_v1.dao.EDatabase
import com.example.examen_v1.dao.ESqliteHelper
import com.example.examen_v1.model.ListaReproduccion
import com.example.examen_v1.view.*

class MainActivity : AppCompatActivity() {

    var listas = arrayListOf<ListaReproduccion>()
    var idItemSeleccionado = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EDatabase.database = ESqliteHelper(this)
        listas = EDatabase.database!!.getAllListas()
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
                "${index} | ${listaReproduccion.getNombre()}"
            }
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_listas, menu);
        val infoLista = menuInfo as AdapterView.AdapterContextMenuInfo;
        val idLista = infoLista.position
        if(idLista != null){
            idItemSeleccionado = listas[idLista].getId()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mc_lv_ver -> {
                try {
                    val idLista = idItemSeleccionado;
                    irActividad(ViewVerLista::class.java, idLista)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.mc_lv_editar-> {
                try {
                    val idLista = idItemSeleccionado;
                    irActividad(ViewEditarLista::class.java, idLista)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.mc_lv_eliminar -> {
                abrirDialogEliminar()
                return true;
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun irActividad(clase: Class<*>, id:Int?){
        val intent = Intent(this, clase)
        intent.putExtra("idLista",id)
        startActivity(intent)
    }

    private fun abrirDialogEliminar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Esta seguro que desea eliminar?")
        builder.setPositiveButton(
            "Si",
            DialogInterface.OnClickListener{ dialog, which ->
                if (idItemSeleccionado.let { EDatabase.database!!.eliminarCancionPorID(it) }){
                    updateViewLista()
                }
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
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