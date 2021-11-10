package com.ss.universitiesdirectory.ui.news

import androidx.lifecycle.ViewModel
import com.ss.universitiesdirectory.repository.news.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getNews(address: String) = newsRepository.downloadRssData(address)
}