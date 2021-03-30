package com.tuan2101.todolist1.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ToDoModel {
    var _id: Int = 0
    val ID: Int
        get() = _id

    var _status: Int = 0
    val Status: Int
        get() = _status
    var _task:String = ""
    val Task: String
        get() = _task


}