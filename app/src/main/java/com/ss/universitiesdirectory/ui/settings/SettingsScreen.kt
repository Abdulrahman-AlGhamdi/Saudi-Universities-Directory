package com.ss.universitiesdirectory.ui.settings

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.preference.PreferenceManager
import com.ss.universitiesdirectory.BuildConfig
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.ui.main.DefaultTopAppBar

private lateinit var vm: SettingsViewModel
private lateinit var sp: SharedPreferences

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel) {
    val context = LocalContext.current

    vm = viewModel
    sp = PreferenceManager.getDefaultSharedPreferences(context)

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = R.string.settings_fragment,
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = { navController.popBackStack() }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                About()
            }
        }
    )
}

@Composable
private fun About() {
    SettingsCard(title = R.string.settings_about) {
        var isAboutDialogOpen by remember { mutableStateOf(false) }
        if (isAboutDialogOpen) AboutDialog { isAboutDialogOpen = false }

        SettingsItem(
            title = R.string.settings_contact_title,
            icon = Icons.Default.Email,
            onClick = { vm.sendEmail() }
        )

        SettingsItem(
            title = R.string.settings_version_title,
            icon = Icons.Default.Info,
            onClick = { isAboutDialogOpen = true }
        )
    }
}

@Composable
private fun AboutDialog(onDismiss: () -> Unit) = AlertDialog(
    onDismissRequest = onDismiss,
    title = {
        val build = BuildConfig.VERSION_CODE
        val version = BuildConfig.VERSION_NAME
        val appVersion = stringResource(id = R.string.settings_version_message, build, version)
        Text(text = appVersion, textAlign = TextAlign.Center)
    },
    icon = {
        Icon(
            painter = painterResource(id = R.drawable.icon_university),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    },
    confirmButton = {}
)