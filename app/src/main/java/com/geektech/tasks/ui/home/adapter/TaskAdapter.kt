package com.geektech.tasks.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geektech.tasks.models.Task
import com.geektech.tasks.databinding.ItemTaskBinding

class TaskAdapter(private val deleteClick:(Task) -> Unit) : Adapter<TaskAdapter.TaskViewHolder>() {
    private var data = arrayListOf<Task>()
    fun addTasks(list: List<Task>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.description
            itemView.setOnLongClickListener{
                deleteClick(task)
                false
            }
        }

    }

}