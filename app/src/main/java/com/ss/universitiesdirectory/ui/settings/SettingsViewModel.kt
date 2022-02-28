package com.ss.universitiesdirectory.ui.settings

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.manager.settings.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsManager
) : ViewModel() {

    fun setAppLanguage(isArabic: Boolean) = settingsRepository.setAppLanguage(isArabic)

    fun sendEmail() = settingsRepository.sendEmail()
}