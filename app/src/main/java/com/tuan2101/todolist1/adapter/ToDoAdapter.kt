package com.tuan2101.todolist1.adapter

import android.app.Activity
import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.OperationApplicationException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.tuan2101.todolist1.MainActivity
import com.tuan2101.todolist1.R
import com.tuan2101.todolist1.database.Task
import com.tuan2101.todolist1.database.TaskDatabaseDao
import com.tuan2101.todolist1.databinding.TaskFragmentBinding
import kotlinx.coroutines.*


class ToDoAdapter(val application: Application , val database: TaskDatabaseDao, val activity: MainActivity): ListAdapter<Task,
        ToDoAdapter.ViewHolder>(SleepNightDiffCallback()) {



    val scope = MainScope() + Job()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    private suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun delete(taskId: Int) {
        withContext(Dispatchers.IO) {
            database.delete(taskId)
        }
    }

    fun deleteItem(position: Int) {
        scope.launch {
            withContext(Dispatchers.IO) {
                val item = getItem(position)
                delete(item.taskId)
                submitList(currentList)
            }
        }
    }

    fun editItem(position: Int): Bundle {
        val item = getItem(position)
        val bundle = Bundle()
        bundle.putInt("taskId", item.taskId)
        bundle.putString("task", item.task)
        return bundle
    }


    fun getContext(): Context {
        return application.applicationContext
    }

    fun getActivity(): Activity {
        return activity
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.toDoCheckBox.isChecked = item.status!!
        holder.binding.toDoCheckBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->
            if (b) {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val newTask = database.get(item.taskId) ?: return@withContext
                        newTask.status = true
                        update(newTask)




                        println("=================================================================")
                        println("set")
                        println("checked")
                        println(newTask.status!!)
                        println("=================================================================")
                    }
                }
            } else {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val newTask = database.get(item.taskId) ?: return@withContext
                        newTask.status = false
                        update(newTask)


                        println("=================================================================")
                        println("unchecked")
                        println(newTask.status!!)
                        println("=================================================================")
                    }
                }
            }
        })

        holder.bind(item)


    }

    class ViewHolder(val binding: TaskFragmentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.task = task
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskFragmentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }




}


//k can thong bao list trong db thay doi nua, luon observe va tra ve list trong db
class SleepNightDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
