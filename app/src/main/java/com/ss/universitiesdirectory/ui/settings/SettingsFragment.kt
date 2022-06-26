package com.ss.universitiesdirectory.ui.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.preference.PreferenceManager
import com.ss.universitiesdirectory.BuildConfig
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.Gray
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White
import com.ss.universitiesdirectory.utils.LanguageHelper
import java.util.*

private lateinit var vm: SettingsViewModel
private lateinit var sp: SharedPreferences

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel) {
    val context = LocalContext.current

    vm = viewModel
    sp = PreferenceManager.getDefaultSharedPreferences(context)

    Scaffold(
        topBar = { SettingsTopBar(navController) },
        content = { SettingsContent(it) },
        snackbarHost = {}
    )
}

@Composable
private fun SettingsTopBar(navController: NavHostController) = CenterAlignedTopAppBar(
    title = { Text(text = "Settings") },
    navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
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
fun SettingsContent(paddingValues: PaddingValues) = Column(Modifier.padding(paddingValues)) {
    SettingsHeader(icon = Icons.Default.Settings, text = "Application Configuration")
    SettingsItem(title = "Theme", onItemClick = { vm.openThemeDialog = true })
    SettingsItem(title = "Language", onItemClick = { vm.openLanguageDialog = true })
    Divider(color = Gray)
    SettingsHeader(icon = Icons.Default.Info, text = "About")
    SettingsItem(
        title = "Contact Me",
        description = "Any suggestions or issues please don't hesitate to contact me"
    ) { vm.sendEmail() }

    SettingsItem(
        title = "Application Version",
        isClickable = false,
        description = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    )

    if (vm.openThemeDialog) ThemeDialog()
    if (vm.openLanguageDialog) LanguageDialog()
}

@Composable
fun SettingsHeader(icon: ImageVector, tint: Color = PrimaryColor, text: String) = Row(
    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)
) {
    Icon(imageVector = icon, contentDescription = null, tint = tint)
    Text(text = text, color = tint, modifier = Modifier.padding(start = 32.dp))
}

@Composable
fun SettingsItem(
    title: String,
    titleColor: Color = Black,
    description: String? = null,
    descriptionColor: Color = Color.Gray,
    isClickable: Boolean = true,
    onItemClick: () -> Unit = {}
) = Column(
    modifier = Modifier
        .then(if (isClickable) Modifier.clickable { onItemClick() } else Modifier)
        .fillMaxWidth()
        .padding(24.dp)
) {
    Text(
        text = title,
        color = titleColor,
        fontSize = 16.sp,
        modifier = Modifier.padding(start = 56.dp)
    )
    description?.let {
        Text(
            text = description,
            color = descriptionColor,
            modifier = Modifier.padding(top = 8.dp, start = 56.dp)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ThemeDialog() {
    var selected = remember { sp.getBoolean("isDarkTheme", false) }

    AlertDialog(
        onDismissRequest = { vm.openThemeDialog = false },
        properties = DialogProperties(dismissOnBackPress = false),
        title = { Text(text = "UI Mode") },
        confirmButton = {
            Button(onClick = { vm.openThemeDialog = false }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "DISMISS")
            }
        },
        text = {
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = !selected, enabled = selected, onClick = {
                        setTheme(darkMode = false)
                        selected = false
                    })
                    Text(text = "Light", color = Black, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selected, enabled = !selected, onClick = {
                        setTheme(darkMode = true)
                        selected = true
                    })
                    Text(text = "Dark", color = Black, fontSize = 16.sp)
                }
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LanguageDialog() {
    val language = sp.getString(LanguageHelper.CURRENT_LANGUAGE, Locale.getDefault().language)
    var selected = remember { language == "ar" }

    AlertDialog(
        onDismissRequest = { vm.openLanguageDialog = false },
        properties = DialogProperties(dismissOnBackPress = false),
        title = { Text(text = "UI Mode") },
        confirmButton = {
            Button(onClick = { vm.openLanguageDialog = false }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "DISMISS")
            }
        },
        text = {
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = !selected, enabled = selected, onClick = {
                        vm.setAppLanguage(isArabic = false)
                        selected = false
                    })
                    Text(text = "English", color = Black, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selected, enabled = !selected, onClick = {
                        vm.setAppLanguage(isArabic = true)
                        selected = true
                    })
                    Text(text = "Arabic", color = Black, fontSize = 16.sp)
                }
            }
        }
    )
}

private fun setTheme(darkMode: Boolean) {
    sp.edit().putBoolean("isDarkTheme", darkMode).apply()

    if (darkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    vm.openThemeDialog = false
}