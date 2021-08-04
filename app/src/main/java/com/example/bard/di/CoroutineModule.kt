package com.example.bard.di

import com.example.bard.di.annotation.IoDisPatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ApplicationComponent::class)
object CoroutineModule {

    @IoDisPatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}