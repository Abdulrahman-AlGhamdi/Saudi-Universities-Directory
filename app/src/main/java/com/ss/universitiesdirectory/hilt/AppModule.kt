package com.ss.universitiesdirectory.hilt

import com.ss.universitiesdirectory.ui.news.NewsRepository
import com.ss.universitiesdirectory.ui.universities.UniversitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsRepository() = NewsRepository()

    @Provides
    @Singleton
    fun provideUniversities() = UniversitiesRepository()
}