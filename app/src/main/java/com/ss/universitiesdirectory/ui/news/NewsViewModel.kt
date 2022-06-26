package com.ss.universitiesdirectory.ui.news

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.universitiesdirectory.repository.news.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    val snackBarHost = SnackbarHostState()
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    val newsStatus = newsRepository.newsStatus

    fun getUniversityNews(address: String) = viewModelScope.launch {
        newsRepository.downloadRssData(address)
    }
}