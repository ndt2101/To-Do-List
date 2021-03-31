package com.tuan2101.todolist1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.todolist1.model.ToDoViewModel
import com.tuan2101.todolist1.model.ToDoViewModelFactory
import com.tuan2101.todolist1.R
import com.tuan2101.todolist1.adapter.ToDoAdapter
import com.tuan2101.todolist1.database.TaskDatabase
import com.tuan2101.todolist1.databinding.FragmentToDoListBinding

class ToDoListFragment : Fragment() {


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

        binding.lifecycleOwner = this //liveData

        val manager = LinearLayoutManager(activity)

        binding.taskRecyclerView.layoutManager = manager

        toDoViewModel.navigateToCreateNewTask.observe(viewLifecycleOwner, Observer { task ->
            task?.let {
                val bundle = bundleOf("taskId" to task.taskId)
                this.findNavController().navigate(R.id.action_toDoListFragment_to_newTaskFragment, bundle)
                toDoViewModel.doneNavigating()
            }
        })

        toDoViewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

}