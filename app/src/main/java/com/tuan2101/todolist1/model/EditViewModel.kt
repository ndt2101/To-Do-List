package com.tuan2101.todolist1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuan2101.todolist1.database.Task
import com.tuan2101.todolist1.database.TaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditViewModel (private val taskId: Int,
                     val database: TaskDatabaseDao
): ViewModel() {


    private val _navigateToTaskList = MutableLiveData<Boolean?>()

    val navigateToTaskList: LiveData<Boolean?>
        get() = _navigateToTaskList

    fun doneNavigating() {
        _navigateToTaskList.value = null
    }

    fun onSaveTaskClick() {
        _navigateToTaskList.value = true

//        println("======================================================")
//        println("clicked")
//        println("======================================================")
    }

    private suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    fun saveTaskClicked(task: String) {


        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                println("taskIDdddddddddddddddddddddddddddddddddddddddddddddddddddddddd        $taskId")
                val newTask = database.get(taskId) ?: return@withContext

                println("heereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeefasdfsddffasffaf")
                newTask.task = task
                println("======================================================")
                println("${newTask.status} + ${newTask.task} + ${newTask.taskId}")
                println("======================================================")
                update(newTask)
            }
        }
    }
}
