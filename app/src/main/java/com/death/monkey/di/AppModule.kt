package com.death.monkey.di

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.death.monkey.repositories.ApplicationRepositoryImpl
import com.death.monkey.repositories.ApplicationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesPackageManager(@ApplicationContext applicationContext: Context): PackageManager = applicationContext.packageManager
}