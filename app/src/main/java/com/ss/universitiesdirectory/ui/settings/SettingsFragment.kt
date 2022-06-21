package com.ss.universitiesdirectory.ui.settings

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ss.universitiesdirectory.BuildConfig
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentSettingsBinding
import com.ss.universitiesdirectory.ui.theme.Black
import com.ss.universitiesdirectory.ui.theme.Gray
import com.ss.universitiesdirectory.ui.theme.PrimaryColor
import com.ss.universitiesdirectory.ui.theme.White
import com.ss.universitiesdirectory.utils.navigateTo
import com.ss.universitiesdirectory.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel by viewModels<SettingsViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
                Scaffold(
                    topBar = { SettingsTopBar { findNavController().popBackStack() } },
                    content = { SettingsContent(it) },
                    snackbarHost = {}
                )
            }
        }
    }

    @Composable
    private fun SettingsTopBar(onPopUpCallback: () -> Unit) = CenterAlignedTopAppBar(
        title = { Text(text = getString(R.string.fragment_settings)) },
        navigationIcon = {
            IconButton(onClick = onPopUpCallback) {
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
            text = getString(R.string.preference_application_configuration)
        )
        SettingsItem(title = getString(R.string.preference_theme_title)) {
            val directions = SettingsFragmentDirections
            val actions = directions.actionSettingsFragmentToThemeFragment()
            findNavController().navigateTo(actions, R.id.settingsFragment)
        }
        SettingsItem(title = getString(R.string.preference_language_title)) {
            val directions = SettingsFragmentDirections
            val actions = directions.actionSettingsFragmentToThemeFragment()
            findNavController().navigateTo(actions, R.id.settingsFragment)
        }
        Divider(color = Gray)
        SettingsHeader(
            icon = Icons.Default.Info,
            text = getString(R.string.preference_about)
        )
        SettingsItem(
            title = getString(R.string.preference_contact_me_title),
            description = getString(R.string.preference_contact_me_summary)
        ) { viewModel.sendEmail() }

        SettingsItem(
            title = getString(R.string.preference_version_title),
            isClickable = false,
            description = getString(
                R.string.preference_version_summary,
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE
            )
        )
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
}