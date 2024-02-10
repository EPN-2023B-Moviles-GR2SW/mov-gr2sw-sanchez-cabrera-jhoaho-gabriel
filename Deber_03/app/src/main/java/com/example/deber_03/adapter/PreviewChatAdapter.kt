package com.example.deber_03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.PreviewChat
import com.example.deber_03.R

class PreviewChatAdapter(private val chatList: List<PreviewChat>): RecyclerView.Adapter<PreviewChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return  PreviewChatViewHolder(layoutInflater.inflate(R.layout.item_chat, parent, false))
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: PreviewChatViewHolder, position: Int) {
        val item = chatList[position]
        holder.render(item)
    }
}