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

    private val _navigateToCreateNewTask = MutableLiveData<Int>()
    val navigateToCreateNewTask: LiveData<Int>
        get() = _navigateToCreateNewTask

    fun onCreateNewTaskNavigated() {
        _navigateToCreateNewTask.value = null
    }

    fun onCreateButtonClicked(id: Int) {
        _navigateToCreateNewTask.value = id
    }

    private var newTask = MutableLiveData<Task?>()

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

    private suspend fun delete(task: Task) {
        withContext(Dispatchers.IO) {
            database.delete(task.taskId)
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

        }
    }


}


//    var _id: Int = 0
//    val ID: Int
//        get() = _id
//
//    var _status: Int = 0
//    val Status: Int
//        get() = _status
//    var _task:String = ""
//    val Task: String
//        get() = _task