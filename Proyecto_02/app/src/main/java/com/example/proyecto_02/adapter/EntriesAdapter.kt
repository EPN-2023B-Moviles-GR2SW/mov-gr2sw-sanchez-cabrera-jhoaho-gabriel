package com.example.proyecto_02.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_02.R
import com.example.proyecto_02.entities.Entrada

class EntriesAdapter(private val entriesList: List<Entrada>) : RecyclerView.Adapter<EntriesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntriesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return  EntriesHolder(layoutInflater.inflate(R.layout.item_entry, parent, false))
    }

    override fun getItemCount(): Int {
        return entriesList.size
    }

    override fun onBindViewHolder(holder: EntriesHolder, position: Int) {
        val item = entriesList[position]
        holder.render(item)
    }

}
