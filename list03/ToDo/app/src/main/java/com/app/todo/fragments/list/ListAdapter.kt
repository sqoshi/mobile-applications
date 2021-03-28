package com.app.todo.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.todo.R
import com.app.todo.data.Task
import kotlinx.android.synthetic.main.task_row.view.*


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var taskList = emptyList<Task>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currItem = taskList[position]
        holder.itemView.taskName.text = currItem.name.toString()
        holder.itemView.textViewDate.text = currItem.date.toString()
        holder.itemView.textViewDesc.text = currItem.description.toString()
    }

    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }


}