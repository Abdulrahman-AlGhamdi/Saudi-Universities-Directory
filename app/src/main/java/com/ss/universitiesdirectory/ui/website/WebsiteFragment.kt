package com.ss.universitiesdirectory.ui.website

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WebsiteScreen(navController: NavHostController, websiteUrl: String) {
    val context = LocalContext.current

    Scaffold(
        topBar = { WebsiteTopBar(navController, context, websiteUrl) },
        content = { WebsiteContent(it, websiteUrl) }
    )
}

@Composable
private fun WebsiteTopBar(
    navController: NavHostController,
    context: Context,
    websiteUrl: String
) = CenterAlignedTopAppBar(
    title = { Text(text = "Website") },
    navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    },
    actions = {
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(context, intent, null)
        }) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
        }
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = PrimaryColor,
        titleContentColor = White,
        navigationIconContentColor = White,
        actionIconContentColor = White
    )
)

@Composable
private fun WebsiteContent(paddingValues: PaddingValues, websiteUrl: String) = Box(
    modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
) {
    AndroidView(
        factory = {
            WebView(it).apply {
                this.webViewClient = WebViewClient()
                this.settings.javaScriptEnabled = true
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                loadUrl(websiteUrl)
            }
        }, update = {
            it.loadUrl(websiteUrl)
        }
    )
}