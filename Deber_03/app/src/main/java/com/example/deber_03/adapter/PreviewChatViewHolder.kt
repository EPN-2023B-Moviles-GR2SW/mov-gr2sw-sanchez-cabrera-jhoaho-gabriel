package com.example.deber_03.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.deber_03.PreviewChat
import com.example.deber_03.R
import com.google.android.material.imageview.ShapeableImageView

class PreviewChatViewHolder(view: View): ViewHolder(view) {

    val nameChat = view.findViewById<TextView>(R.id.tv_name_chat)
    val msg = view.findViewById<TextView>(R.id.tv_msg)
    val timeMark = view.findViewById<TextView>(R.id.tv_time)
    val point = view.findViewById<TextView>(R.id.tv_point)
    val photo = view.findViewById<ShapeableImageView>(R.id.siv_chat)

    @SuppressLint("ResourceAsColor")
    fun render(previewChat: PreviewChat){
        nameChat.text = previewChat.name
        msg.text = previewChat.msg
        timeMark.text = previewChat.time
        point.text = " · "
        Glide.with(photo.context).load(previewChat.pfp).into(photo)
    }
}