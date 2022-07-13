package com.paultorres.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paultorres.movies.model.local.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDBInterface(): MoviesDBInterface
}

