package com.ss.universitiesdirectory.repository.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DetailsManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DetailsManager {

    override fun openApp(stringUri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringUri))
        val chooser = Intent.createChooser(intent, "Open app")
        chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, chooser, null)
    }
}