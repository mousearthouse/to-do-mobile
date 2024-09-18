package com.example.todolist

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.todolist.databinding.ActivityMainBinding
import java.util.UUID
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

data class Task(
    val desc: String,
    val done: Boolean,
    val id: UUID,
    @Expose(serialize = false) val isEdited: Boolean
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

        binding.loadBtn.setOnClickListener {
            tasksList.clear()
            tasksList.addAll(loadTasksFromFile(this, "tasks.json"))
            adapter.submitList(tasksList.toList())
        }

        binding.saveBtn.setOnClickListener {
            saveTasksToFile(this, tasksList, "tasks.json")
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

    fun saveTasksToFile(context: Context, tasks: List<Task>, fileName: String) {
        val gson = Gson()
        val jsonString = gson.toJson(tasks)
        Log.d(",kznm", jsonString)

        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use { outputStream ->
            outputStream.write(jsonString.toByteArray())
        }
    }

    fun loadTasksFromFile(context: Context, fileName: String): List<Task> {
        val file = File(context.filesDir, fileName)

        if (!file.exists()) {
            return emptyList()
        }

        val jsonString = FileInputStream(file).use { inputStream ->
            inputStream.bufferedReader().use { it.readText() }
        }
        Log.d(",kznm", jsonString)

        val gson = Gson()
        val taskListType = object : TypeToken<List<Task>>() {}.type
        return gson.fromJson(jsonString, taskListType)
    }
}
