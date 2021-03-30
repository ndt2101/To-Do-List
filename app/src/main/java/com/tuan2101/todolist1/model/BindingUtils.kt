package com.tuan2101.todolist1.model

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.tuan2101.todolist1.database.Task

@BindingAdapter("task")
fun CheckBox.setTask(item: Task?) {
    item?.let {
        text = item.task
    }
}