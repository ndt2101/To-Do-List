package com.tuan2101.todolist1.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tuan2101.todolist1.database.Task
import com.tuan2101.todolist1.database.TaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoViewModel(val database: TaskDatabaseDao,
                    application: Application
): AndroidViewModel(application) {

    private val _navigateToCreateNewTask = MutableLiveData<Task>()
    val navigateToCreateNewTask: LiveData<Task>
        get() = _navigateToCreateNewTask


    fun doneNavigating() {
        _navigateToCreateNewTask.value = null
    }

    private var newTask = MutableLiveData<Task?>()

    val tasks = database.getAllTask()

    init {
        initializeNewTask()
    }

    private fun initializeNewTask() {
        viewModelScope.launch {
            newTask.value = getNewTaskFromDataBase()
        }
    }

    private suspend fun getNewTaskFromDataBase(): Task? {
        var task = database.getNewTask()
        if (task?.endTimeMilli != task?.startTimeMilli) {
            task = null
        }
        return task
    }

    private suspend fun delete(taskId: Int) {
        withContext(Dispatchers.IO) {
            database.delete(taskId)
        }
    }

    private suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }



    fun onCreateNewTask() {
        viewModelScope.launch {
            val newCreatedTask = Task()

            insert(newCreatedTask)

            newTask.value = getNewTaskFromDataBase()

            _navigateToCreateNewTask.value = newTask.value
        }
    }

    fun deleteEmptyTask(taskId: Int) {
        viewModelScope.launch {
            delete(taskId)
        }
    }


}
