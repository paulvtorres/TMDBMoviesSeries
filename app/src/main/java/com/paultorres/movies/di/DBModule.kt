package com.paultorres.movies.di

import android.content.Context
import androidx.room.Room
import com.paultorres.movies.data.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "tmdb.db"
        )
            .fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation()
            .build()
    }
}