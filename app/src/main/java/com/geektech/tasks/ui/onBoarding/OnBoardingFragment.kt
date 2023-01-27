package com.geektech.tasks.ui.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geektech.tasks.R
import com.geektech.tasks.data.Pref
import com.geektech.tasks.databinding.FragmentOnBoardingBinding
import com.geektech.tasks.ui.onBoarding.adapter.onBoardingAdapter

class OnBoardingFragment : Fragment() {
    lateinit var binding: FragmentOnBoardingBinding
    lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        pref = Pref(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = onBoardingAdapter {
            pref.saveSeen()
            findNavController().navigateUp()
        }
        binding.taskVP.adapter = adapter
        binding.indicator.setViewPager(binding.taskVP)
        adapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)
    }

}