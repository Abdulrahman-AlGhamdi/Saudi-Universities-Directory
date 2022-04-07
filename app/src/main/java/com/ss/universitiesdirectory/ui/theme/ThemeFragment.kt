package com.ss.universitiesdirectory.ui.theme

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentThemeBinding

class ThemeFragment : DialogFragment(R.layout.fragment_theme) {

    private lateinit var binding: FragmentThemeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentThemeBinding.inflate(LayoutInflater.from(requireContext()))
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        binding.composeView.apply {
            this.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            this.setContent {
                var selected = remember { sharedPreferences.getBoolean("isDarkTheme", false) }

                Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = !selected, enabled = selected, onClick = {
                            setTheme(darkMode = false)
                            selected = false
                        })
                        Text(text = "Light", color = if (isSystemInDarkTheme()) White else Black)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selected, enabled = !selected, onClick = {
                            setTheme(darkMode = true)
                            selected = true
                        })
                        Text(text = "Dark", color = if (isSystemInDarkTheme()) White else Black)
                    }
                }
            }
        }

        val builder = AlertDialog.Builder(requireContext()).apply {
            this.setView(binding.root)
            this.setTitle(R.string.preference_theme_title)
        }

        return builder.create()
    }

    private fun setTheme(darkMode: Boolean) {
        sharedPreferences.edit().putBoolean("isDarkTheme", darkMode).apply()

        if (darkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        dismiss()
    }
}