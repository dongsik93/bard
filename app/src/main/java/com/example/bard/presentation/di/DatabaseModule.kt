package com.example.bard.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.bard.data.source.local.DsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}