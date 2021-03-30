package com.tuan2101.todolist1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.todolist1.MainActivity
import com.tuan2101.todolist1.Model.ToDoModel
import com.tuan2101.todolist1.R

class ToDoAdapter(val activity: MainActivity): RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private lateinit var toDoList: List<ToDoModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.task_layout,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item: ToDoModel = toDoList[position]
        holder.toDoCheckBox.text = item.Task
        holder.toDoCheckBox.isChecked = item.Status.toString().toBoolean()
    }

    fun setTask(taskList: MutableList<ToDoModel>) {
        toDoList = taskList
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val toDoCheckBox: CheckBox = itemView.findViewById(R.id.toDoCheckBox)
    }
}