package com.paultorres.movies.data

import androidx.paging.*
import com.paultorres.movies.data.api.MoviesAPIInterface
import com.paultorres.movies.data.local.MoviesDatabase
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val api: MoviesAPIInterface,
    private val db: MoviesDatabase
) {
    @ExperimentalPagingApi
    fun getMovies(type_movie: String, type: String): Flow<PagingData<MoviesEntity>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE, // required
              //  prefetchDistance = PAGE_SIZE, // default: pageSize
              //  enablePlaceholders = true, // default: true
              //  initialLoadSize = PAGE_SIZE * 3, // default: pageSize * 3
              //  maxSize = PagingConfig.MAX_SIZE_UNBOUNDED, // default: Int.MAX_VALUE
              //  jumpThreshold = 0 // default: Int.MIN_VALE
            ),
            remoteMediator = MoviesRemoteMediator(
                api, db, type_movie, type, null
            ),
            pagingSourceFactory = { db.moviesDBInterface().getPagingMovie(type_movie, type) }
        ).flow

    }

    @ExperimentalPagingApi
    fun getSearch(type_movie: String, query: String): Flow<PagingData<MoviesEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            remoteMediator = MoviesRemoteMediator(
                api, db, type_movie, null, query
            ),
            pagingSourceFactory = { db.moviesDBInterface().getSearchPagingMovie(type_movie, query) }

        ).flow
    }

    suspend fun getMovie(movie_type: String, id: Int): MoviesEntity {
        return db.moviesDBInterface().getSearchMovie(movie_type, id)
    }
}