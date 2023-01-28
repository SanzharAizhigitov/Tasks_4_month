package com.geektech.tasks.ui.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geektech.tasks.R
import com.geektech.tasks.databinding.ItemOnBoardingBinding
import com.geektech.tasks.ext.loadImage
import com.geektech.tasks.models.onBoard

class onBoardingAdapter(private val onClick: () -> Unit) :
    Adapter<onBoardingAdapter.onBoardingViewHolger>() {
    var data = arrayListOf(
        onBoard(
            "Привет!",
            "Это приложение создано чтобы помогать тебе выполнять задачи",
            R.raw.task_anim_1
            ),
        onBoard(
            "Перемещайся по экранам!",
            "Используй конпки снизу чтобы перемещаться",
            R.raw.bottom_nav_anim
        ),
        onBoard(
            "Добавляй задачи с помощью соотвественной кнопки!",
            "Эта кнопка расположена на экране с домиком",
            R.raw.add_anim
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onBoardingViewHolger {
        return onBoardingViewHolger(
            ItemOnBoardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: onBoardingViewHolger, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class onBoardingViewHolger(private val binding: ItemOnBoardingBinding) :
        ViewHolder(binding.root) {
        fun bind(onBoard: onBoard) {
            binding.tvTitle2.text = onBoard.title
            binding.tvDesc2.text = onBoard.desc
            onBoard.anim?.let { binding.onBoardingIv.setAnimation(it) }
            binding.getstartBtn.setOnClickListener { onClick() }

            binding.getstartBtn.isVisible = adapterPosition == data.lastIndex
        }
    }

}
