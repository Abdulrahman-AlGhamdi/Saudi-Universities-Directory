package com.ss.universitiesdirectory.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ss.universitiesdirectory.BuildConfig
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.utils.LanguageHelper
import com.ss.universitiesdirectory.utils.navigateTo

class SettingsFragment : PreferenceFragmentCompat() {

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
            if (newValue as Boolean) LanguageHelper.changeCurrentLanguage(requireActivity(), "ar")
            else LanguageHelper.changeCurrentLanguage(requireActivity(), "en")
            true
        }

        contactMe?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Abdulrahman.SS.AlGhamdi@Gmail.Com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "${requireContext().packageName}: suggestion/issue email")
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Sending Email"))
            true
        }
    }

    private fun showData() {
        val version = findPreference(getString(R.string.preference_version_key)) as? Preference
        version?.summary = getString(R.string.preference_version_summary, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}