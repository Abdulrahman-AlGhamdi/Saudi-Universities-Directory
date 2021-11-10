package com.ss.universitiesdirectory.ui.splash

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentSplashBinding
import com.ss.universitiesdirectory.utils.viewBinding
import kotlinx.coroutines.delay

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        endSplash()
    }

    private fun init() {
        val vectorDrawable = binding.splashLogo.drawable as AnimatedVectorDrawable
        vectorDrawable.start()
    }

    private fun endSplash() = lifecycleScope.launchWhenCreated {
        delay(3500)
        val directions = SplashFragmentDirections
        val action = directions.actionSplashFragmentToUniversitiesFragment()
        findNavController().navigate(action)
    }
}