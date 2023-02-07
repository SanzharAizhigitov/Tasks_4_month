package com.geektech.tasks.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geektech.tasks.App
import com.geektech.tasks.R
import com.geektech.tasks.data.Pref
import com.geektech.tasks.databinding.FragmentSplashBinding
import com.geektech.tasks.models.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SplashFragment : Fragment() {
    private lateinit var pref: Pref
    private lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentSplashBinding
    private val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        pref = Pref(requireContext())
        val tasks = App.db.taskDao().getAll()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            db.collection(uid).get().addOnSuccessListener {
                val data = it.toObjects(Task::class.java)
                val duplicates = tasks.minus(data.toSet())
                if (duplicates.isNotEmpty()) {
                    db.collection(uid).add(duplicates).addOnSuccessListener {
                        findNavController().navigate(R.id.navigation_home)
                    }.addOnFailureListener {
                        startFragment()
                    }
                }
            }.addOnFailureListener {
                startFragment()
            }
        } else {
            startFragment()
        }
    }

    private fun startFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
        if (!pref.isUserSeen()) {
            findNavController().navigate(R.id.onBoardingFragment)
        } else if (auth.currentUser == null) {
            findNavController().navigate(R.id.authFragment)
        } else {
            findNavController().navigate(R.id.navigation_home)
        } }, 5000)
    }
}