package com.app.todo.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.todo.R
import com.app.todo.model.Task
import kotlinx.android.synthetic.main.fragment_add.view.*
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currItem = taskList[position]
        holder.itemView.taskName.text = currItem.name
        holder.itemView.textViewDesc.text = currItem.description
        holder.itemView.textViewDate.text = currItem.date.year.toString() +
                "-" + addZeroPrefix(currItem.date.month.toString()) +
                "-" + addZeroPrefix(currItem.date.day.toString())


        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    private fun addZeroPrefix(string: String): String {
        return if (string.length == 1)
            "0$string"
        else
            string
    }


}