package com.example.bard.di

import android.content.Context
import androidx.room.Room
import com.example.bard.db.DsDataBase
import com.example.bard.repository.DsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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
    fun provideRepository(database: DsDataBase): DsRepository = DsRepository.getInstance(database)
}