package com.tuan2101.todolist1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.todolist1.MainActivity
import com.tuan2101.todolist1.model.ToDoViewModel
import com.tuan2101.todolist1.R

class ToDoAdapter(): RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.task_layout,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        var item: ToDoViewModel = toDoList[position]
//        holder.toDoCheckBox.text = item.Task
//        holder.toDoCheckBox.isChecked = item.Status.toString().toBoolean()


    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val toDoCheckBox: CheckBox = itemView.findViewById(R.id.toDoCheckBox)
    }
}