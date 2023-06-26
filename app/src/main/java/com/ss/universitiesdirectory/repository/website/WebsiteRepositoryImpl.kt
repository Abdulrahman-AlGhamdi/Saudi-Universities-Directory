package com.ss.universitiesdirectory.repository.website

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WebsiteRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : WebsiteRepository {

    override fun openWebsiteInBrowser(websiteUrl: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
        ContextCompat.startActivity(context, intent, null)
    }
}