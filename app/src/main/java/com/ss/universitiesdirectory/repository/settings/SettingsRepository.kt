package com.ss.universitiesdirectory.repository.settings

import android.content.Context
import android.content.Intent
import com.ss.universitiesdirectory.utils.LanguageHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun setAppLanguage(isArabic: Boolean) {
        if (isArabic) LanguageHelper.changeCurrentLanguage(context, "ar")
        else LanguageHelper.changeCurrentLanguage(context, "en")
    }

    fun sendEmail() {
        val email   = "Abdulrahman.SS.AlGhamdi@Gmail.Com"
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
}