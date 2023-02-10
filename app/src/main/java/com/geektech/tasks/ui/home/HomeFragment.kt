package com.geektech.tasks.ui.home

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geektech.tasks.App
import com.geektech.tasks.R
import com.geektech.tasks.models.Task
import com.geektech.tasks.databinding.FragmentHomeBinding
import com.geektech.tasks.ext.isOnline
import com.geektech.tasks.ui.home.adapter.TaskAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: TaskAdapter
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    var uid: String? = null
    var docId: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(this::deleteClick, this::update)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tasksRv.adapter = adapter
        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
        setDataFromServer()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setDataFromServer() {
        if (requireContext().isOnline()) {
            getTasks()
        } else {
            setData()
        }
    }

    private fun getTasks() {
        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        if (uid != null) {
            db.collection(uid!!).get().addOnSuccessListener {
                val data = it.toObjects(Task::class.java)
                adapter.addTasks(data)
            }.addOnFailureListener {}
        }
    }

    private fun setData() {
        val tasks = App.db.taskDao().getAll()
        adapter.addTasks(tasks)
    }

    private fun deleteClick(task: Task) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Delete?")
        alertDialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, pos: Int) {
                dialog?.cancel()
            }
        })
        alertDialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, pos: Int) {
                App.db.taskDao().delete(task)
                if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                    db.collection(FirebaseAuth.getInstance().currentUser?.uid!!)
                        .document(task.id.toString()).delete()
                } else {
                    findNavController().navigate(R.id.authFragment)
                }
                setData()
            }
        })
        alertDialog.create().show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun update(task: Task, pos:Int) {
        uid = FirebaseAuth.getInstance().currentUser?.uid
        if (requireContext().isOnline()){
            if (uid!=null){
                db.collection(uid!!).get().addOnSuccessListener {
                    val  id = it.documents[pos].id
                    findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToTaskFragment(task, id))
                }.addOnFailureListener{
                    findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToTaskFragment(task))
                }
            }
        }else{
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToTaskFragment(task))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}