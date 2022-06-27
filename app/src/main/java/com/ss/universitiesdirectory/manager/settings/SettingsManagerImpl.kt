package com.ss.universitiesdirectory.manager.settings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.ss.universitiesdirectory.utils.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsManager {

    override fun setAppTheme(isDarkMode: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putBoolean(Constant.PREFERENCE_THEME_KEY, isDarkMode).apply()

        if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun sendEmail() {
        val email = EMAIL_ADDRESS
        val subject = "${context.packageName}: suggestion/issue email"

        val intent = Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            this.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            this.putExtra(Intent.EXTRA_SUBJECT, subject)
            this.type = "message/rfc822"
        }, "Sending Email").apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }

    private companion object SettingsConstants {
        private const val EMAIL_ADDRESS = "Abdulrahman.SS.AlGhamdi@Gmail.Com"
    }
}