package com.ss.universitiesdirectory.ui.website

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
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
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.ui.main.DefaultTopAppBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WebsiteScreen(
    navController: NavHostController,
    websiteUrl: String?,
    context: Context = LocalContext.current,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = R.string.website_fragment,
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                        startActivity(context, intent, null)
                    }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                    }
                }
            )
        },
        content = { paddingValues ->
            websiteUrl?.let { WebsiteContent(paddingValues, websiteUrl) }
        }
    )
}

@Composable
@SuppressLint("SetJavaScriptEnabled")
private fun WebsiteContent(paddingValues: PaddingValues, websiteUrl: String) = AndroidView(
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
    },
    modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
)