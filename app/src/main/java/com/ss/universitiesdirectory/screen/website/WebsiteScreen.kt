package com.ss.universitiesdirectory.screen.website

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.ui.common.DefaultCenterTopAppBar
import com.ss.universitiesdirectory.ui.common.DefaultIconButton
import com.ss.universitiesdirectory.ui.common.DefaultScaffold
import com.ss.universitiesdirectory.ui.theme.SaudiUniversitiesComposeTheme

@Composable
fun WebsiteScreen(
    websiteUrl: String?,
    onBrowserClick: () -> Unit,
    onBackClick: () -> Unit
) {
    DefaultScaffold(
        topBar = { WebsiteTopAppBar(onBrowserClick = onBrowserClick, onBackClick = onBackClick) },
        content = { websiteUrl?.let { WebsiteContent(websiteUrl) } }
    )
}

@Composable
private fun WebsiteTopAppBar(
    onBrowserClick: () -> Unit,
    onBackClick: () -> Unit
) = DefaultCenterTopAppBar(
    title = R.string.website_screen,
    actions = { DefaultIconButton(icon = Icons.Default.ExitToApp, onClick = onBrowserClick) },
    navigationIcon = { DefaultIconButton(onClick = onBackClick, icon = Icons.Default.ArrowBack) }
)

@Composable
@SuppressLint("SetJavaScriptEnabled")
private fun WebsiteContent(websiteUrl: String) = AndroidView(
    factory = {
        WebView(it).apply {
            this.webViewClient = WebViewClient()
            this.settings.javaScriptEnabled = true
            loadUrl(websiteUrl)
        }
    }, update = { it.loadUrl(websiteUrl) },
    modifier = Modifier.fillMaxSize()
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun WebsiteScreenPreview() = SaudiUniversitiesComposeTheme {
    WebsiteScreen(
        websiteUrl = "",
        onBrowserClick = {},
        onBackClick = {}
    )
}