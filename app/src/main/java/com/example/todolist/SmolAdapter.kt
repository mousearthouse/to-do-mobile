package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SmolAdapter(
    val deleteTask: (Int) -> Unit,
    val editTask: (Int, String) -> Unit,
    val setCheck: (Int) -> Unit
): ListAdapter<Task, SmolViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Task> = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(
                oldTask:Task, newTask:Task
            ): Boolean {
                return oldTask.id == newTask.id
            }

            override fun areContentsTheSame(
                oldTask:Task, newTask:Task
            ): Boolean {
                return oldTask == newTask
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmolViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_view, parent, false)

            return SmolViewHolder(view, deleteTask, editTask, setCheck)
    }

    override fun onBindViewHolder(holder: SmolViewHolder, position: Int) {
        holder.textView.text = getItem(position).desc
        holder.doneChk.isChecked = getItem(position).done

        holder.editText.setText(getItem(position).desc)
        if (getItem(position).isEdited) {
            holder.editText.isVisible = true
            holder.textView.isVisible = false
        } else {
            holder.editText.isVisible = false
            holder.textView.isVisible = true
        }
    }
}

class SmolViewHolder(
    view: View,
    val deleteTask: (Int) -> Unit,
    val editTask: (Int, String) -> Unit,
    val setCheck: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    val textView: TextView
    val editBtn: Button
    val deleteBtn: Button
    val doneChk: CheckBox
    val editText: EditText

    init {
        textView = view.findViewById(R.id.task_text)
        editBtn = view.findViewById(R.id.edit_btn)
        deleteBtn = view.findViewById(R.id.delete_btn)
        doneChk = view.findViewById(R.id.checkbox)
        editText = view.findViewById(R.id.edit_text)

        deleteBtn.setOnClickListener {
            deleteTask(bindingAdapterPosition)
        }

        editBtn.setOnClickListener {
            editTask(bindingAdapterPosition, editText.text.toString())

        }

        doneChk.setOnCheckedChangeListener { buttonView, isChecked ->  setCheck(bindingAdapterPosition)}

    }
}
