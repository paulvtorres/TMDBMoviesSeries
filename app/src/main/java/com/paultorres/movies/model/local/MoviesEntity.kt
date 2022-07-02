package com.paultorres.movies.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_table", primaryKeys = ["id", "movie_type"])
data class MoviesEntity(
//    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val overview: String?,
    val poster_path: String,
    val title: String,
    val original_language: String,
    val release_date: String,
    val popularity: String,
    val movie_type: String,
    val type: String
)

