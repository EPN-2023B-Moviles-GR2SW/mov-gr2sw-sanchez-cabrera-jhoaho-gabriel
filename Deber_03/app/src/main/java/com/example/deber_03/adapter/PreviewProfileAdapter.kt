package com.example.deber_03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.PreviewProfile
import com.example.deber_03.R

class PreviewProfileAdapter(private val profileList: List<PreviewProfile>): RecyclerView.Adapter<PreviewProfileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewProfileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PreviewProfileViewHolder(layoutInflater.inflate(R.layout.item_search, parent, false))
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun onBindViewHolder(holder: PreviewProfileViewHolder, position: Int) {
        val item = profileList[position]
        holder.render(item)
    }
}