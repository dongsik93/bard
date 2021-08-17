package com.example.bard.di

import com.example.bard.data.repositories.NoteRepositoryImpl
import com.example.bard.data.source.local.DsDataBase
import com.example.bard.di.annotation.IoDispatcher
import com.example.bard.domain.repositories.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideNoteRepository(
        database: DsDataBase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NoteRepository = NoteRepositoryImpl(database, ioDispatcher)
}