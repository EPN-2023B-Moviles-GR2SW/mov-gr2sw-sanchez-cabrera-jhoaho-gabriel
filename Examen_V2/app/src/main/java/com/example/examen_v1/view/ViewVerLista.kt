package com.example.examen_v1.view

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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.examen_v1.R
import com.example.examen_v1.dao.EDatabase
import com.example.examen_v1.model.Cancion
import com.example.examen_v1.model.ListaReproduccion

class ViewVerLista : AppCompatActivity() {

    var idLista = -1
    var idItemSeleccionado = -1
    var listaCanciones = arrayListOf<Cancion>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_ver_lista)

        idLista = intent.getIntExtra("idLista", -1)

        val botonAgregarCancion = findViewById<Button>(R.id.btn_vl_agregar_cancion)
        botonAgregarCancion.setOnClickListener {
            irActividad(ViewCrearCancion::class.java, idLista)
        }

        updateViewLista()
        llenarCampos()

    }

    private fun updateViewLista() {
        val listView = findViewById<ListView>(R.id.lv_vl_lc)
        listaCanciones = EDatabase.database!!.getAllCancionesPorLista(idLista)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaCanciones!!.mapIndexed { index, cancion ->
                "${index} | ${cancion.getNombre()} \n ${cancion.getDuracion()}"
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
        inflater.inflate(R.menu.menu_canciones, menu);
        val infoCancion = menuInfo as AdapterView.AdapterContextMenuInfo;
        val idCancion = infoCancion.position
        if (idLista != null) {
            idItemSeleccionado =
                listaCanciones[idCancion].getId()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mc_lv_c_editar -> {
                try {
                    val idCancion = idItemSeleccionado
                    irActividad(ViewEditarCancion::class.java, idCancion)
                } catch (e: Throwable) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.mc_lv_c_eliminar -> {
                abrirDialogEliminar()
                return true;
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun irActividad(clase: Class<*>, id: Int?) {
        val intent = Intent(this, clase)
        intent.putExtra("idCancion", id)
        intent.putExtra("idLista", idLista)
        startActivity(intent)
    }

    private fun abrirDialogEliminar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Esta seguro que desea eliminar?")
        builder.setPositiveButton(
            "Si",
            DialogInterface.OnClickListener { dialog, which ->
                if (idItemSeleccionado.let { EDatabase.database!!.eliminarCancionPorID(it, idLista) }) {
                    updateViewLista()
                }
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    private fun llenarCampos(){
        val txtId = findViewById<TextView>(R.id.tv_vl_id)
        val txtNombre = findViewById<TextView>(R.id.tv_vl_nombre)
        val txtReproduccion = findViewById<TextView>(R.id.tv_vl_ra)
        val txtDuracion = findViewById<TextView>(R.id.tv_vl_duracion)

        if(idLista != -1){
            val lista = EDatabase.database!!.consultarListaPorID(idLista)
            if(lista!==null){
                txtId.text = lista.getId().toString()
                txtNombre.text = lista.getNombre()
                txtReproduccion.text = if(lista.isAleatoria()) "Habilitada" else "Deshabilitada"
                txtDuracion.text = lista.getDuracion().toString()
            }
        }
    }
    override fun onRestart() {
        super.onRestart()
        updateViewLista()
        llenarCampos()
    }

    override fun onResume() {
        super.onResume()
        updateViewLista()
        llenarCampos()
    }
}