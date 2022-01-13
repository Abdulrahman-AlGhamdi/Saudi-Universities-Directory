package com.ss.universitiesdirectory.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ss.universitiesdirectory.BuildConfig
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.utils.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        onPreferenceClick()
        showData()
    }

    private fun onPreferenceClick() {
        val theme     = findPreference(getString(R.string.preference_theme_key)) as? Preference
        val contactMe = findPreference(getString(R.string.preference_contact_me_key)) as? Preference
        val language  = findPreference(getString(R.string.preference_language_key)) as? Preference

        theme?.setOnPreferenceClickListener {
            val directions = SettingsFragmentDirections
            val actions    = directions.actionSettingsFragmentToThemeFragment()
            findNavController().navigateTo(actions, R.id.settingsFragment)
            true
        }

        language?.setOnPreferenceChangeListener { _, newValue ->
            viewModel.setAppLanguage(newValue as Boolean)
            true
        }

        contactMe?.setOnPreferenceClickListener {
            viewModel.sendEmail()
            true
        }
    }

    private fun showData() {
        val version = findPreference(getString(R.string.preference_version_key)) as? Preference
        version?.summary = getString(R.string.preference_version_summary, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}