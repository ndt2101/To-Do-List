package com.tuan2101.todolist1.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuan2101.todolist1.database.TaskDatabaseDao

class NewTaskViewModelFactory(private val taskId: Int,
    private val dataSource: TaskDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTaskViewModel::class.java)) {
            return NewTaskViewModel(taskId,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}