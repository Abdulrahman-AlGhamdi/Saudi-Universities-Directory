package com.ss.universitiesdirectory.ui.splash

import android.os.Bundle
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentSplashBinding
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.viewBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val directions = SplashFragmentDirections

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
                val scale = remember { Animatable(0f) }

                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_university),
                        contentDescription = null,
                        modifier = Modifier.scale(scale.value)
                    )
                }

                LaunchedEffect(key1 = true) {
                    scale.animateTo(
                        targetValue = 0.8f,
                        animationSpec = tween(durationMillis = 1000)
                    )

                    val action = directions.actionSplashFragmentToUniversitiesFragment()
                    findNavController().navigateTo(action, R.id.splashFragment)
                }
            }
        }
    }
}