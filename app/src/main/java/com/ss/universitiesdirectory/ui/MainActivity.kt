package com.ss.universitiesdirectory.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ss.universitiesdirectory.data.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.ui.details.DetailsScreen
import com.ss.universitiesdirectory.ui.settings.SettingsScreen
import com.ss.universitiesdirectory.ui.settings.SettingsViewModel
import com.ss.universitiesdirectory.ui.splash.SplashScreen
import com.ss.universitiesdirectory.ui.theme.SaudiUniversitiesComposeTheme
import com.ss.universitiesdirectory.ui.universities.UniversitiesScreen
import com.ss.universitiesdirectory.ui.universities.UniversitiesViewModel
import com.ss.universitiesdirectory.ui.website.WebsiteScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SaudiUniversitiesComposeTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }

    @Composable
    private fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "splash") {
            composable(route = "splash", content = {
                SplashScreen(navController)
            })
            composable(route = "universities", content = {
                val viewModel = hiltViewModel<UniversitiesViewModel>()
                UniversitiesScreen(navController, viewModel)
            })
            composable(route = "details", content = {
                val university = navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<UniversityModel>("university")

                university?.let { DetailsScreen(navController, university) }
            })
            composable(route = "website", content = {
                val websiteUrl = navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<String>("url")

                websiteUrl?.let { WebsiteScreen(navController, websiteUrl) }
            })
            composable(route = "settings", content = {
                val viewModel = hiltViewModel<SettingsViewModel>()
                SettingsScreen(navController, viewModel)
            })
        }
    }
}