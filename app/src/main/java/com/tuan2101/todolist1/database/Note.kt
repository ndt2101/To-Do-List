package com.tuan2101.todolist1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_task_table")
data class Task(
        @PrimaryKey(autoGenerate = true)
        var taskId: Int = 0,

        @ColumnInfo(name = "status")
        var status: String? = null,

        @ColumnInfo(name = "task")
        var task: String? = null
)