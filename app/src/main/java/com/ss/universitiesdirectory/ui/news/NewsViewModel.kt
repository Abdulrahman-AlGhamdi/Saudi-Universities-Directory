package com.ss.universitiesdirectory.ui.news

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedInputStream
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getNews(address: String) = newsRepository.downloadRssData(address)
}