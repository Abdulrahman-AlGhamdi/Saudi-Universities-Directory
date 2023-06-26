package com.ss.universitiesdirectory.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ss.universitiesdirectory.model.univeristy.UniversityModel
import com.ss.universitiesdirectory.screen.settings.SettingsScreen
import com.ss.universitiesdirectory.screen.settings.SettingsViewModel
import com.ss.universitiesdirectory.screen.splash.SplashScreen
import com.ss.universitiesdirectory.screen.universities.UniversitiesScreen
import com.ss.universitiesdirectory.screen.universities.UniversitiesViewModel
import com.ss.universitiesdirectory.screen.website.WebsiteScreen
import com.ss.universitiesdirectory.screen.website.WebsiteViewModel
import com.ss.universitiesdirectory.screen.details.DetailsScreen
import com.ss.universitiesdirectory.screen.details.DetailsViewModel

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
        val viewModel = hiltViewModel<UniversitiesViewModel>()
        val universitiesState by viewModel.universitiesState.collectAsState()

        UniversitiesScreen(
            universitiesState = universitiesState,
            onUniversitySearch = viewModel::getSearchList,
            onDetailsClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("university", it)
                navController.navigate(route = Screen.DetailsScreen.route)
            },
            onSettingsClick = { navController.navigate(Screen.SettingsScreen.route) }
        )
    })
    composable(route = Screen.DetailsScreen.route, content = {
        val viewModel = hiltViewModel<DetailsViewModel>()
        val currentBackStackEntry = navController.currentBackStackEntry?.savedStateHandle
        val previousBackStackEntry = navController.previousBackStackEntry?.savedStateHandle
        val university = previousBackStackEntry?.get<UniversityModel>("university")

        DetailsScreen(
            university = university,
            onWebsiteClick = {
                currentBackStackEntry?.set("url", it)
                navController.navigate(route = Screen.WebsiteScreen.route)
            },
            onAppClick = viewModel::openApp,
            onBackClick = navController::popBackStack
        )
    })
    composable(route = Screen.WebsiteScreen.route, content = {
        val viewModel = hiltViewModel<WebsiteViewModel>()
        val websiteUrl = navController.previousBackStackEntry?.savedStateHandle?.get<String>("url")

        WebsiteScreen(
            websiteUrl = websiteUrl,
            onBrowserClick = { viewModel.openWebsiteInBrowser(websiteUrl) },
            onBackClick = navController::popBackStack
        )
    })
    composable(route = Screen.SettingsScreen.route, content = {
        val viewModel = hiltViewModel<SettingsViewModel>()

        SettingsScreen(
            onContactClick = viewModel::openTwitter,
            onBackClick = navController::popBackStack
        )
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