package com.example.foodyrecipes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @IoDispatchers
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatchers
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatchers

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatchers