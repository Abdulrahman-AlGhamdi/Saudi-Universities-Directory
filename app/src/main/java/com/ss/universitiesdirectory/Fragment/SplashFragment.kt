package com.ss.universitiesdirectory.Fragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
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
        val handler = Handler()
        handler.postDelayed({
            findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToUniversitiesFragment())
        }, 3500)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}