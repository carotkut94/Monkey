package com.death.monkey.di

import android.content.pm.PackageManager
import com.death.monkey.repositories.ApplicationRepositoryImpl
import com.death.monkey.repositories.ApplicationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesApplicationRepository(packageManager: PackageManager) : ApplicationsRepository = ApplicationRepositoryImpl(packageManager)
}
