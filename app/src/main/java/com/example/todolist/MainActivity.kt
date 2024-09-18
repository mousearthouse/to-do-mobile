package com.example.todolist

import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.todolist.databinding.ActivityMainBinding

data class Task (val desc: String,
                 val done: Boolean)  {
}

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    val tasksList = arrayListOf<Task>()
    val adapter = SmolAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recycledView = binding.tasksRecycler
        recycledView.layoutManager = LinearLayoutManager(this) // Добавляем LayoutManager
        recycledView.adapter = adapter

        binding.addTask.setOnClickListener {
            addTask("NewTask")
        }
    }

    private fun addTask(description: String) {
        tasksList.add(Task(description, false))
        adapter.submitList(tasksList.toList()) // Создаем неизменяемую копию списка
    }
}
