package com.ss.universitiesdirectory.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ss.universitiesdirectory.ui.main.Navigation
import com.ss.universitiesdirectory.ui.theme.SaudiUniversitiesComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SaudiUniversitiesComposeTheme {
                val navController = rememberAnimatedNavController()
                Navigation(navController = navController)
            }
        }
    }
}