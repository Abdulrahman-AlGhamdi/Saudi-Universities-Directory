package com.ss.universitiesdirectory.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.ui.main.Screen
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.White

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(0f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_university),
            contentDescription = null,
            modifier = Modifier.scale(scale.value),
            colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) White else Black)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.scale(scale.value),
            color = if (isSystemInDarkTheme()) White else Black
        )
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(durationMillis = 1000)
        )

        navController.popBackStack()
        navController.navigate(route = Screen.UniversitiesScreen.route)
    }
}