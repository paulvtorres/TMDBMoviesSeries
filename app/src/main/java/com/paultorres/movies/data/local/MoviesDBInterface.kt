package com.paultorres.movies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paultorres.movies.model.local.MoviesEntity

@Dao
interface MoviesDBInterface {

    @Query("select * from movie_table WHERE movie_type = :movie_type AND type = :type order by id asc")
    fun getPagingMovie(movie_type: String, type: String): PagingSource<Int, MoviesEntity>


    @Query("select * from movie_table WHERE movie_type = :movie_type AND title lIKE '%' || :query || '%' order by id asc")
    fun getSearchPagingMovie(movie_type: String, query: String): PagingSource<Int, MoviesEntity>

    @Query("select * from movie_table WHERE movie_type = :movie_type AND id = :id")
    suspend fun getSearchMovie(movie_type: String, id: Int): MoviesEntity

    @Query("select * from movie_table WHERE movie_type = :movie_type AND type = :type order by id asc")
    suspend fun getTestMovie(movie_type: String, type: String): List<MoviesEntity>


    @Query("DELETE FROM movie_table WHERE movie_type = :movie_type AND type = :type")
    suspend fun deleteAllMovies(movie_type: String, type: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MoviesEntity>)
}