package com.tuan2101.todolist1.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuan2101.todolist1.R
import com.tuan2101.todolist1.database.TaskDatabase
import com.tuan2101.todolist1.databinding.FragmentNewTaskBinding
import com.tuan2101.todolist1.model.NewTaskViewModel
import com.tuan2101.todolist1.model.NewTaskViewModelFactory

import android.content.Context
import android.view.inputmethod.InputMethodManager


class NewTaskFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentNewTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_task, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao

        val taskId = requireArguments().getInt("taskId")

        val viewModelFactory = NewTaskViewModelFactory(taskId,dataSource) //chua sua

        val newTaskViewModel = ViewModelProvider(this, viewModelFactory).get(NewTaskViewModel::class.java)

        binding.newTaskViewModel = newTaskViewModel

        binding.lifecycleOwner = this //liveData

        newTaskViewModel.navigateToTaskList.observe(viewLifecycleOwner, Observer {
            it?.let {
                var taskText = binding.newTaskText.text.toString()
                if (taskText != "") {
                    newTaskViewModel.saveTaskClicked(taskText)
                    this.findNavController().navigate(R.id.action_newTaskFragment_to_toDoListFragment)
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
                    newTaskViewModel.doneNavigating()
                }
            }
        })

        return binding.root
    }






}