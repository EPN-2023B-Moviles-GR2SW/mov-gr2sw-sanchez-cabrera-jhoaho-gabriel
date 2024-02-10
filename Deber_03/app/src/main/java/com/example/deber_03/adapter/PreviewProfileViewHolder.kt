package com.example.deber_03.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.deber_03.PreviewProfile
import com.example.deber_03.R
import com.google.android.material.imageview.ShapeableImageView

class PreviewProfileViewHolder(view: View): ViewHolder(view) {

    val nameProfile = view.findViewById<TextView>(R.id.tv_name_profile)
    val descProfile = view.findViewById<TextView>(R.id.tv_desc_profile)
    val cross = view.findViewById<TextView>(R.id.tv_cross)
    val photo = view.findViewById<ShapeableImageView>(R.id.siv_search)

    fun render(previewProfile: PreviewProfile) {
        nameProfile.text = previewProfile.name
        descProfile.text = previewProfile.desc
        cross.text = "x"
        Glide.with(photo.context).load(previewProfile.pfp).into(photo)
    }


}