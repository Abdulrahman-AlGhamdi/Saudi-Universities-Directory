package com.ss.universitiesdirectory.ui.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.ui.details.DetailsScreen
import com.ss.universitiesdirectory.ui.settings.SettingsScreen
import com.ss.universitiesdirectory.ui.splash.SplashScreen
import com.ss.universitiesdirectory.ui.universities.UniversitiesScreen
import com.ss.universitiesdirectory.ui.website.WebsiteScreen

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun Navigation(navController: NavHostController) = AnimatedNavHost(
    navController = navController,
    startDestination = Screen.SplashScreen.route,
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popEnterTransition = { EnterTransition.None },
    popExitTransition = { ExitTransition.None }
) {
    composable(route = Screen.SplashScreen.route, content = {
        SplashScreen(navController)
    })
    composable(route = Screen.UniversitiesScreen.route, content = {
        UniversitiesScreen(navController)
    })
    composable(route = Screen.DetailsScreen.route, content = {
        val university = navController
            .previousBackStackEntry
            ?.savedStateHandle
            ?.get<UniversityModel>("university")

        DetailsScreen(navController, university)
    })
    composable(route = Screen.WebsiteScreen.route, content = {
        val websiteUrl = navController
            .previousBackStackEntry
            ?.savedStateHandle
            ?.get<String>("url")

        WebsiteScreen(navController, websiteUrl)
    })
    composable(route = Screen.SettingsScreen.route, content = {
        SettingsScreen(navController)
    })
}

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash")
    object UniversitiesScreen : Screen(route = "universities")
    object DetailsScreen : Screen(route = "details")
    object WebsiteScreen : Screen(route = "website")
    object SettingsScreen : Screen(route = "settings")

    fun withParams(vararg args: String): String = buildString {
        append(route)
        args.forEach { append("/{$it}") }
    }

    fun <T> withArgs(vararg params: T): String = buildString {
        append(route)
        params.forEach { append("/$it") }
    }
}