package com.tuan2101.todolist1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.todolist1.database.Task
import com.tuan2101.todolist1.database.TaskDatabase
import com.tuan2101.todolist1.database.TaskDatabaseDao
import com.tuan2101.todolist1.databinding.TaskFragmentBinding
import com.tuan2101.todolist1.generated.callback.OnClickListener
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ToDoAdapter(val taskListener: TaskListener,val database: TaskDatabaseDao): ListAdapter<Task,
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.toDoCheckBox.isChecked = item.status!!
        holder.binding.toDoCheckBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener() { compoundButton: CompoundButton, b: Boolean ->
            if(b) {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val newTask = database.get(item.taskId)?:return@withContext
                        newTask.status = true
                        update(newTask)




                        println("=================================================================")
                        println("set")
                        println("checked")
                        println(newTask.status!!)
                        println("=================================================================")
                    }
                }
            }
            else {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val newTask = database.get(item.taskId)?:return@withContext
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

        holder.bind(taskListener ,item)


    }

    class ViewHolder(val binding: TaskFragmentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(onClickListener: TaskListener,task: Task) {
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

class SleepNightDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskListener(val clickListener: (TaskId: Int) -> Unit) {
    fun onClick(task: Task) = clickListener(task.taskId)
}