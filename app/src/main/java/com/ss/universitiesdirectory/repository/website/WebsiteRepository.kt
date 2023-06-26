package com.ss.universitiesdirectory.repository.website

interface WebsiteRepository {

    fun openWebsiteInBrowser(websiteUrl: String?)
}