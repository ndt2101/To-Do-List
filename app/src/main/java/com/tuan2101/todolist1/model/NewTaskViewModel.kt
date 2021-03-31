package com.tuan2101.todolist1.model

import androidx.lifecycle.*
import com.tuan2101.todolist1.database.TaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewTaskViewModel(private val taskId: Int = 0,
        val database: TaskDatabaseDao
): ViewModel() {
    private val viewModelJob = Job()

    private val _navigateToTaskList = MutableLiveData<Boolean?>()

    val navigateToTaskList: LiveData<Boolean?>
        get() = _navigateToTaskList

    fun doneNavigating() {
        _navigateToTaskList.value = null
    }

    fun onSaveTaskClick() {
        _navigateToTaskList.value = true
    }


    fun saveTaskClicked(task: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newTask = database.get(taskId) ?: return@withContext
                newTask.task = task
                newTask.endTimeMilli = System.currentTimeMillis()
                database.update(newTask)
            }
        }
    }

}