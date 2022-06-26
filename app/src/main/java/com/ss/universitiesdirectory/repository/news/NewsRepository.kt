package com.ss.universitiesdirectory.repository.news

import kotlinx.coroutines.flow.StateFlow

interface NewsRepository {

    val newsStatus: StateFlow<NewsRepositoryImpl.NewsStatus>

    suspend fun downloadRssData(address: String): Unit?
}