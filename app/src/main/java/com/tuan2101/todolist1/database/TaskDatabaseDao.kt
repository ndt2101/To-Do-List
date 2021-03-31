package com.tuan2101.todolist1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface TaskDatabaseDao {

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * from daily_task_table WHERE taskId = :key")
    suspend fun get(key: Int): Task?

    @Query("DELETE from daily_task_table WHERE taskId = :key")
    suspend fun delete(key: Int)

    @Query("SELECT * from daily_task_table WHERE taskId = :key")
    fun getNightWithId(key: Long): LiveData<Task>

    @Query("SELECT * FROM daily_task_table ORDER BY taskId DESC")
    fun getAllTask(): LiveData<List<Task>>

    @Query("SELECT * FROM daily_task_table ORDER BY taskId DESC LIMIT 1")
    suspend fun getNewTask(): Task?
}