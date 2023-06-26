package com.ss.universitiesdirectory.screen.website

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.repository.website.WebsiteRepository
import javax.inject.Inject

class WebsiteViewModel @Inject constructor(
    private val websiteRepository: WebsiteRepository
) : ViewModel() {

    fun openWebsiteInBrowser(websiteUrl: String?) {
        websiteRepository.openWebsiteInBrowser(websiteUrl)
    }
}