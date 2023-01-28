package com.geektech.tasks.ui.taskFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geektech.tasks.App
import com.geektech.tasks.models.Task
import com.geektech.tasks.databinding.FragmentTaskBinding


class TaskFragment : Fragment() {
    lateinit var binding: FragmentTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBtn.setOnClickListener {
            App.db.taskDao().insert(
                Task(
                    title = binding.outlinedTextFieldTitle.editText?.text.toString(),
                    description = binding.outlinedTextFieldDesc.editText?.text.toString()
                )
            )
            findNavController().navigateUp()
        }
    }

    companion object {
        const val RESULT_TASK = "result.task"
    }
}
