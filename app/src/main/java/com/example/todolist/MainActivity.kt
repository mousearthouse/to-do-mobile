package com.example.todolist

import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.todolist.databinding.ActivityMainBinding
import java.util.UUID

data class Task(
    val desc: String,
    val done: Boolean,
    val id: UUID,
    val isEdited: Boolean
) {
}

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    val tasksList = arrayListOf<Task>()
    val adapter = SmolAdapter(this::deleteTask, this::editTask, this::setCheck)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recycledView = binding.tasksRecycler
        recycledView.layoutManager = LinearLayoutManager(this)
        recycledView.adapter = adapter


        binding.addTask.setOnClickListener {
            if (getText() != "") {
                addTask(getText())
            } else {
                Toast.makeText(this, "Write something", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getText(): String {
        return binding.writeTask.text.toString()
    }

    private fun addTask(description: String) {
        tasksList.add(Task(description, false, UUID.randomUUID(), false))
        adapter.submitList(tasksList.toList())
    }

    private fun setCheck(position: Int) {
        val editedCheck = tasksList[position].copy(done = !tasksList[position].done)
        tasksList[position] = editedCheck
        adapter.submitList(tasksList.toList())
    }

    private fun deleteTask(position: Int) {
        tasksList.removeAt(position)
        adapter.submitList(tasksList.toList())
    }

    private fun editTask(position: Int, text: String) {
        val thisTask = tasksList[position]
        if (thisTask.isEdited == false) {
            val isEditedCheck = thisTask.copy(isEdited = true)
            tasksList[position] = isEditedCheck
            adapter.submitList(tasksList.toList())
        }
        else {
            val isEditedCheck = thisTask.copy(desc = text, isEdited = false)
            tasksList[position] = isEditedCheck
            adapter.submitList(tasksList.toList())
        }
    }
}
