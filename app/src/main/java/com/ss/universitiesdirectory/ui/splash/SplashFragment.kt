package com.ss.universitiesdirectory.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentSplashBinding
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startSplash()
        endSplash()
    }

    private fun startSplash() {
        binding.logo.animation  = AnimationUtils.loadAnimation(context, R.anim.from_bottom_centered)
        binding.title.animation = AnimationUtils.loadAnimation(context, R.anim.from_bottom)
    }

    private fun endSplash() = lifecycleScope.launch(Dispatchers.Main) {
        delay(1100)
        val directions = SplashFragmentDirections
        val action     = directions.actionSplashFragmentToUniversitiesFragment()
        findNavController().navigateTo(action, R.id.splashFragment)
    }
}