package com.ss.universitiesdirectory.di

import com.ss.universitiesdirectory.manager.settings.SettingsManager
import com.ss.universitiesdirectory.manager.settings.SettingsManagerImpl
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepository
import com.ss.universitiesdirectory.repository.universities.UniversitiesRepositoryImpl
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
    abstract fun bindUniversities(
        universitiesRepositoryImpl: UniversitiesRepositoryImpl
    ): UniversitiesRepository

    @Binds
    @ViewModelScoped
    abstract fun provideSettingsManager(
        settingsManagerImpl: SettingsManagerImpl
    ): SettingsManager
}