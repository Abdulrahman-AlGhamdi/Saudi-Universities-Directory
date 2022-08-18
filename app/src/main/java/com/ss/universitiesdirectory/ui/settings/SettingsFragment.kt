package com.ss.universitiesdirectory.ui.settings

import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.preference.PreferenceManager
import com.ss.universitiesdirectory.BuildConfig
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.Gray
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White
import com.ss.universitiesdirectory.utils.Constant

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
@OptIn(ExperimentalMaterial3Api::class)
private fun SettingsTopBar(navController: NavHostController) = CenterAlignedTopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.settings_fragment),
            fontFamily = FontFamily(Font(R.font.quest_regular))
        )
    },
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
    SettingsHeader(
        icon = Icons.Default.Settings,
        text = stringResource(id = R.string.settings_configuration)
    )
    SettingsItem(
        title = stringResource(id = R.string.settings_theme),
        onItemClick = { vm.openThemeDialog = true }
    )
    Divider(color = Gray, modifier = Modifier.padding(top = 8.dp))
    SettingsHeader(
        icon = Icons.Default.Info,
        text = stringResource(id = R.string.settings_about)
    )
    SettingsItem(
        title = stringResource(id = R.string.settings_contact_title),
        description = stringResource(id = R.string.settings_contact_message),
        onItemClick = { vm.sendEmail() }
    )
    SettingsItem(
        title = stringResource(id = R.string.settings_version_title),
        isClickable = false,
        description = stringResource(
            id = R.string.settings_version_message,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
    )

    if (vm.openThemeDialog) ThemeDialog()
}

@Composable
fun SettingsHeader(icon: ImageVector, tint: Color = PrimaryColor, text: String) = Row(
    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
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
        .padding(16.dp)
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
    val selected = remember { sp.getBoolean(Constant.PREFERENCE_THEME_KEY, false) }

    AlertDialog(
        onDismissRequest = { vm.openThemeDialog = false },
        shape = RoundedCornerShape(16.dp),
        title = { Text(text = stringResource(id = R.string.settings_theme)) },
        confirmButton = {
            Button(
                onClick = { vm.openThemeDialog = false },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.settings_dismiss))
            }
        },
        text = {
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = !selected, enabled = selected, onClick = {
                        vm.setAppMode(isDarkMode = false)
                        vm.openThemeDialog = false
                    })
                    Text(
                        text = stringResource(id = R.string.settings_theme_light),
                        color = Black,
                        fontSize = 16.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selected, enabled = !selected, onClick = {
                        vm.setAppMode(isDarkMode = true)
                        vm.openThemeDialog = false
                    })
                    Text(
                        text = stringResource(id = R.string.settings_theme_dark),
                        color = Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}