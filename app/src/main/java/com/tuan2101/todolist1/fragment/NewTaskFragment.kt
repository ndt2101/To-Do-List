package com.tuan2101.todolist1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tuan2101.todolist1.R
import com.tuan2101.todolist1.database.TaskDatabase
import com.tuan2101.todolist1.databinding.FragmentNewTaskBinding
import com.tuan2101.todolist1.model.NewTaskViewModel
import com.tuan2101.todolist1.model.NewTaskViewModelFactory


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

        return binding.root
    }


}