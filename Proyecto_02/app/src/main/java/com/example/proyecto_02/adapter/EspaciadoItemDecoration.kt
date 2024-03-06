package com.example.proyecto_02.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EspaciadoItemDecoration(private val espacioVertical: Int, private val espacioHorizontal: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Agrega el espaciado solo a los elementos, no al final
        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = espacioVertical
        }
        outRect.right = espacioHorizontal
        outRect.left = espacioHorizontal
    }
}
