package com.ss.universitiesdirectory

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.ss.universitiesdirectory.utils.Constant
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        defaultSettings()
    }

    private fun defaultSettings() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkMode  = preferences.getBoolean(Constant.PREFERENCE_THEME_KEY, false)

        if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}