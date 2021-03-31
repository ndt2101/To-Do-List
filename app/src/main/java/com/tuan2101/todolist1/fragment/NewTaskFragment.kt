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
import androidx.activity.addCallback
import com.tuan2101.todolist1.model.ToDoViewModel
import com.tuan2101.todolist1.model.ToDoViewModelFactory


class NewTaskFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentNewTaskBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_task, container, false)
        binding.lifecycleOwner = this //liveData

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao

        val taskId = requireArguments().getInt("taskId")

        val viewModelFactory = NewTaskViewModelFactory(taskId,dataSource)

        val newTaskViewModel = ViewModelProvider(this, viewModelFactory).get(NewTaskViewModel::class.java)

        val toDoViewModelFactory = ToDoViewModelFactory(dataSource, application)

        val toDoViewModel = ViewModelProvider(this, toDoViewModelFactory).get(ToDoViewModel::class.java)

        binding.newTaskViewModel = newTaskViewModel





        newTaskViewModel.navigateToTaskList.observe(viewLifecycleOwner, Observer {

            it?.let {
                var taskText = binding.newTaskText.text.toString()
                println("++++++++++++++++++++++++++++++++++++++++++++++++navigate")
                if (taskText.isNotEmpty()) {
                    newTaskViewModel.saveTaskClicked(taskText)
                    this.findNavController().navigate(R.id.action_newTaskFragment_to_toDoListFragment)
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
                    newTaskViewModel.doneNavigating()
                }
                else toDoViewModel.deleteEmptyTask(taskId)
            }
        })


//        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            toDoViewModel.deleteEmptyTask(taskId)
//        }
//
//        callback
//
//        callback.remove()


        return binding.root
    }






}