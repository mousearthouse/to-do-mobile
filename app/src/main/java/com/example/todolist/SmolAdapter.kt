package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SmolAdapter: ListAdapter<Task, SmolViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Task> = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(
                oldTask:Task, newTask:Task
            ): Boolean {
                //Task properties may have changed if reloaded from the DB, but ID is fixed
                return oldTask == newTask
            }

            override fun areContentsTheSame(
                oldTask:Task, newTask:Task
            ): Boolean {
                // NOTE: if you use equals, your object must properly override Object#equals()
                // Incorrectly returning false here will result in too many animations.
                return oldTask == newTask
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmolViewHolder {

            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_view, parent, false)

            return SmolViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmolViewHolder, position: Int) {
        holder.textView.text = getItem(position).desc
        holder.doneChk.isChecked = getItem(position).done
    }
}


class SmolViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView
    val editBtn: Button
    val deleteBtn: Button
    val doneChk: CheckBox

    init {
        textView = view.findViewById(R.id.task_text)
        editBtn = view.findViewById(R.id.edit_btn)
        deleteBtn = view.findViewById(R.id.delete_btn)
        doneChk = view.findViewById(R.id.checkbox)
    }
}
