package com.ss.universitiesdirectory.ui.splash

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        init()
        endSplash()

        return binding.root
    }

    private fun init() {
        val vectorDrawable = binding.splashLogo.drawable as AnimatedVectorDrawable
        vectorDrawable.start()
    }

    private fun endSplash() {
        lifecycleScope.launchWhenCreated {
            delay(3500)
            val directions = SplashFragmentDirections
            val action = directions.actionSplashFragmentToUniversitiesFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}