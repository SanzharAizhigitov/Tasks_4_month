package com.geektech.tasks.ui.tasks_frs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.geektech.tasks.Task
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
            setFragmentResult(RESULT_TASK, bundleOf("task" to Task(binding.etTitle.text.toString(), binding.etDesc.text.toString())))
       findNavController().navigateUp()
        }
    }
companion object{
    const val RESULT_TASK = "result.task"
}
}