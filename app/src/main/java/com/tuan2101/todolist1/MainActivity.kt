package com.tuan2101.todolist1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.todolist1.adapter.ToDoAdapter
import com.tuan2101.todolist1.model.ToDoViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show();
    }

}

