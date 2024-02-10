package com.example.deber_03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_03.adapter.PreviewChatAdapter

class RecyclerViewMessages : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_messages)
        initRecylcerView()
    }

    private fun initRecylcerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMessagesIG)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PreviewChatAdapter(PreviewChatProvider.chatList)
    }
}