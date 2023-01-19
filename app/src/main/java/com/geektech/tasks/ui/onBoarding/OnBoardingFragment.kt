package com.geektech.tasks.ui.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.geektech.tasks.R
import com.geektech.tasks.databinding.FragmentOnBoardingBinding
import com.geektech.tasks.ui.onBoarding.adapter.onBoardingAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingFragment : Fragment() {
lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = onBoardingAdapter {
            findNavController().navigateUp()
        }
        binding.taskVP.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.taskVP,){ tab, position->

        }
    }

}