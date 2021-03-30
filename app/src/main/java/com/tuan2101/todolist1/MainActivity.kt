package com.tuan2101.todolist1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.todolist1.Adapter.ToDoAdapter
import com.tuan2101.todolist1.Model.ToDoModel

class MainActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: ToDoAdapter
    private lateinit var taskList: MutableList<ToDoModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        val manager = LinearLayoutManager(this)
        taskRecyclerView.layoutManager = manager
        taskAdapter = ToDoAdapter(this)
        taskRecyclerView.adapter = taskAdapter


        var task = ToDoModel()
        task._id = 1
        task._status = 0
        task._task = "This is Test Task"

        var array = arrayListOf<ToDoModel>(task, task, task, task, task, task, task, task, task, task)

        taskList = array

        taskAdapter.setTask(taskList)
    }
}

