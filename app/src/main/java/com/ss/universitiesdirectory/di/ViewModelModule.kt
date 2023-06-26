package com.ss.universitiesdirectory.di

import com.ss.universitiesdirectory.repository.details.DetailsManager
import com.ss.universitiesdirectory.repository.details.DetailsManagerImpl
import com.ss.universitiesdirectory.repository.settings.SettingsManager
import com.ss.universitiesdirectory.repository.settings.SettingsManagerImpl
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepositoryImpl
import com.ss.universitiesdirectory.repository.website.WebsiteRepository
import com.ss.universitiesdirectory.repository.website.WebsiteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUniversitiesRepository(
        universitiesRepositoryImpl: UniversitiesRepositoryImpl
    ): UniversitiesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindDetailsRepository(
        detailsManagerImpl: DetailsManagerImpl
    ): DetailsManager

    @Binds
    @ViewModelScoped
    abstract fun bindWebsiteRepository(
        websiteRepositoryImpl: WebsiteRepositoryImpl
    ): WebsiteRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSettingsRepository(
        settingsManagerImpl: SettingsManagerImpl
    ): SettingsManager
}