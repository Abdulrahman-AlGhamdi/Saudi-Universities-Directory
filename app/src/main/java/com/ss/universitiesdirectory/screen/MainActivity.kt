package com.ss.universitiesdirectory.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ss.universitiesdirectory.ui.navigation.Navigation
import com.ss.universitiesdirectory.ui.theme.SaudiUniversitiesComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent(content = { SaudiUniversitiesApp() })
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun SaudiUniversitiesApp() = SaudiUniversitiesComposeTheme {
    val navController = rememberAnimatedNavController()
    Navigation(navController = navController)
}