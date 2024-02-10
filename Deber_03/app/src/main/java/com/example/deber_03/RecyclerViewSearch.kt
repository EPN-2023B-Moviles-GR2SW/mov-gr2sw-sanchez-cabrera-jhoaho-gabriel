package com.example.deber_03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.adapter.PreviewProfileAdapter

class RecyclerViewSearch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_recycler_view_search)
        initRecylcerView()
    }

    private fun initRecylcerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerProfilesIG)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PreviewProfileAdapter(PreviewProfileProvider.profileList)
    }
}