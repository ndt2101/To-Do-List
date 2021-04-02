package com.tuan2101.todolist1.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuan2101.todolist1.R
import com.tuan2101.todolist1.database.TaskDatabase
import com.tuan2101.todolist1.databinding.EditFragmentBinding
import com.tuan2101.todolist1.databinding.FragmentNewTaskBinding
import com.tuan2101.todolist1.model.EditViewModel
import com.tuan2101.todolist1.model.EditViewModelFactory

class EditFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: EditFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.edit_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao

        val taskId = requireArguments().getInt("taskId")
        val task = requireArguments().getString("task")

        val editViewModelFactory = EditViewModelFactory(taskId, dataSource)
        val editViewModel = ViewModelProvider(this, editViewModelFactory).get(EditViewModel::class.java)

        binding.newTaskText.setText("$task ")



//        binding.lifecycleOwner = this //liveData
//        println("======================================================")
//        println(editViewModel.navigateToTaskList.value)
//        println("======================================================")
        binding.lifecycleOwner = this //liveData

        editViewModel.navigateToTaskList.observe(viewLifecycleOwner, Observer {


            it?.let {
                println("====================khac==================================")
                println(editViewModel.navigateToTaskList.value)
                println("======================================================")
                println("======================================================")
                println("clicked")
                println("=============================khac=========================")
                editViewModel.saveTaskClicked(binding.newTaskText.text.toString())
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
                this.findNavController().navigate(R.id.action_editFragment_to_toDoListFragment)
                editViewModel.doneNavigating()
                println(editViewModel.navigateToTaskList.value)
            }
        })

        binding.update.setOnClickListener {

            editViewModel.onSaveTaskClick()
//            println("======================================================")
//            println(editViewModel.navigateToTaskList.value)
//            println("======================================================")
//            println("======================================================")
//            println("new clicked")
//            println("======================================================")
//                editViewModel.saveTaskClicked(binding.newTaskText.text.toString())
//            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
//                this.findNavController().navigate(R.id.action_editFragment_to_toDoListFragment)
//                editViewModel.doneNavigating()

        }

        println(editViewModel.navigateToTaskList.value)


        return binding.root
}
}