package com.example.insightcheckemployee.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheckemployee.dataClass.Task
import com.example.insightcheckemployee.R

class TaskAdapter(private var tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    fun addTask(taskTitle: String) {
        val newTask = Task(taskTitle)
        tasks.add(newTask)
        notifyDataSetChanged()
    }

    fun removeCompletedTasks() {
        tasks.removeAll { it.completed }
        notifyDataSetChanged()
    }

    fun getTasks(): List<Task> = tasks

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTask: TextView = itemView.findViewById(R.id.textViewTask)
        private val checkBoxTask: CheckBox = itemView.findViewById(R.id.checkBoxTask)

        fun bind(task: Task) {
            textViewTask.text = task.title
            checkBoxTask.isChecked = task.completed

            checkBoxTask.setOnCheckedChangeListener { _, isChecked ->
                task.completed = isChecked
            }
        }
    }
}
