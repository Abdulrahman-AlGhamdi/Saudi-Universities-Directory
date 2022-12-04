package com.ss.universitiesdirectory.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryColor,
    onPrimary = LightOnPrimaryColor,
    secondary = LightPrimaryColor,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = LightBackgroundColor,
    onBackground = Grey10,
    surface = Color.White,
    surfaceTint = Color.White,
    onSurface = Grey10,
    surfaceVariant = Color.White,
    onSurfaceVariant = ColorOnSurfaceVariantColor,
    outlineVariant = LightOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    onPrimary = DarkOnPrimaryColor,
    secondary = DarkPrimaryColor,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkBackgroundColor,
    onBackground = Grey90,
    surface = DarkSurfaceColor,
    surfaceTint = DarkSurfaceColor,
    onSurface = Grey80,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariantColor,
    outlineVariant = DarkOutlineVariant
)

@Composable
fun SaudiUniversitiesComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    systemUiController.setSystemBarsColor(color = colorScheme.background, darkIcons = !darkTheme)
    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}