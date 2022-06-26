package com.ss.universitiesdirectory.di

import com.ss.universitiesdirectory.repository.news.NewsRepository
import com.ss.universitiesdirectory.repository.news.NewsRepositoryImpl
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUniversities(
        universitiesRepositoryImpl: UniversitiesRepositoryImpl
    ): UniversitiesRepository

    @Binds
    @Singleton
    abstract fun provideNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}