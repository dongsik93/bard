package com.example.bard.di

import android.content.Context
import androidx.room.Room
import com.example.bard.data.db.DsDataBase
import com.example.bard.di.annotation.IoDispatcher
import com.example.bard.repository.DsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DsDataBase = Room.databaseBuilder(
        context,
        DsDataBase::class.java,
        "bard-data.db"
    )
//            .addMigrations(MIGRATION_3_TO_4)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideRepository(
        database: DsDataBase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DsRepository = DsRepository.getInstance(database, ioDispatcher)
}