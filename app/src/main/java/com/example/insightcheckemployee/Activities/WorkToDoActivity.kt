package com.example.insightcheckemployee.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insightcheckemployee.Fragments.Home
import com.example.insightcheckemployee.R
import com.example.insightcheckemployee.adapter.TaskAdapter
import com.example.insightcheckemployee.dataClass.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WorkToDoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonRemoveCompleted: Button
    private lateinit var adapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_to_do)


        val toDoArrow: ImageView = findViewById(R.id.imageView2)
        toDoArrow.setOnClickListener {
            // Replace Home fragment when the ImageView is clicked
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, Home())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
    }

        recyclerView = findViewById(R.id.recyclerView)
        editTextTask = findViewById(R.id.editTextTask)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonRemoveCompleted = findViewById(R.id.buttonRemoveCompleted)

        adapter = TaskAdapter(loadTasks(this))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonAdd.setOnClickListener {
            val task = editTextTask.text.toString().trim()
            if (task.isNotEmpty()) {
                addTask(task)
                editTextTask.text.clear()
            } else {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        buttonRemoveCompleted.setOnClickListener {
            removeCompletedTasks()
        }
    }

    private fun addTask(task: String) {
        adapter.addTask(task)
        saveTasks(this, adapter.getTasks())
    }

    private fun removeCompletedTasks() {
        adapter.removeCompletedTasks()
        saveTasks(this, adapter.getTasks())
    }

    private fun loadTasks(context: Context): MutableList<Task> {
        val sharedPreferences = context.getSharedPreferences("MyTasks", Context.MODE_PRIVATE)
        val jsonTasks = sharedPreferences.getString("tasks", null)
        val tasksType = object : TypeToken<List<Task>>() {}.type
        return Gson().fromJson(jsonTasks, tasksType) ?: mutableListOf()
    }

    private fun saveTasks(context: Context, tasks: List<Task>) {
        val sharedPreferences = context.getSharedPreferences("MyTasks", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val jsonTasks = Gson().toJson(tasks)
        editor.putString("tasks", jsonTasks)
        editor.apply()
    }
}



