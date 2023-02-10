package com.geektech.tasks.ui.taskFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.geektech.tasks.App
import com.geektech.tasks.R
import com.geektech.tasks.models.Task
import com.geektech.tasks.databinding.FragmentTaskBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TaskFragment : Fragment() {
    lateinit var binding: FragmentTaskBinding
    private val db = Firebase.firestore
    private lateinit var navArg: TaskFragmentArgs
    private var task: Task? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            navArg = TaskFragmentArgs.fromBundle(it)
            task = navArg.task
        }
        if (task != null) {
            binding.etDesc.setText(task?.description)
            binding.etTitle.setText(task?.title)
            binding.saveBtn.text = "Update"
        } else {
            binding.saveBtn.text = "Save"
        }
        binding.saveBtn.setOnClickListener {
            if (task != null) {
                onUpdate()
            } else {
                onSave()
            }
        }
    }

    private fun onUpdate() {
        task?.title = binding.etTitle.text.toString()
        task?.description = binding.etDesc.text.toString()
        FirebaseAuth.getInstance()
        updateFireBace()
        task?.let { App.db.taskDao().update(it) }
        findNavController().navigateUp()
    }

    private fun updateFireBace() {
        if (navArg.id != null) {
            FirebaseAuth.getInstance().currentUser?.uid?.let {
                db.collection(it).document(navArg.id.toString()).update(
                    mapOf(
                        "title" to task?.title.toString(),
                        "description" to task?.description.toString()
                    )
                )
            }
        }
    }

    private fun onSave() {
        val task = Task(
            title = binding.outlinedTextFieldTitle.editText?.text.toString(),
            description = binding.outlinedTextFieldDesc.editText?.text.toString()
        )
        putTask(task)
        App.db.taskDao().insert(task)
        findNavController().navigateUp()
    }

    private fun putTask(task: Task) {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            db.collection(it).add(task).addOnSuccessListener {
                Log.e("ololo", "onSave: success")
            }.addOnFailureListener {
                Log.e("ololo", "onSave: fail")
            }
        }
    }

    companion object {
        const val RESULT_TASK = "result.task"
    }
}
//FirebaseAuth.getInstance().currentUser?.let { task?.let { it1 ->
//            db.collection(it.uid).document(task?.id.toString()).set(
//                it1
//            )
//        } }