package com.tuan2101.todolist1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.todolist1.model.ToDoViewModel
import com.tuan2101.todolist1.model.ToDoViewModelFactory
import com.tuan2101.todolist1.R
import com.tuan2101.todolist1.adapter.ToDoAdapter
import com.tuan2101.todolist1.database.TaskDatabase
import com.tuan2101.todolist1.databinding.FragmentToDoListBinding

class ToDoListFragment : Fragment() {


    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskList: MutableList<ToDoViewModel>




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentToDoListBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_to_do_list, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao

        val viewModelFactory = ToDoViewModelFactory(dataSource, application)

        val toDoViewModel = ViewModelProvider(this, viewModelFactory).get(ToDoViewModel::class.java)

        binding.toDoViewModel = toDoViewModel

        var adapter = ToDoAdapter() // chua truyen vao cai gi ca

        binding.taskRecyclerView.adapter = adapter

        val manager = LinearLayoutManager(activity)

        binding.taskRecyclerView.layoutManager = manager




        return binding.root
    }
}