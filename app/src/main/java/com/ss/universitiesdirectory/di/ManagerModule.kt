package com.ss.universitiesdirectory.di

import com.ss.universitiesdirectory.manager.settings.SettingsManager
import com.ss.universitiesdirectory.manager.settings.SettingsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun provideSettingsManager(
        settingsManagerImpl: SettingsManagerImpl
    ): SettingsManager
}