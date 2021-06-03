package com.ss.universitiesdirectory.ui.splash

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.databinding.FragmentSplashBinding

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
        Handler(Looper.getMainLooper()).postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToUniversitiesFragment()
            findNavController().navigate(action)
        }, 3500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}