package com.paultorres.movies.data


import androidx.paging.*
import com.paultorres.movies.data.api.MoviesAPIInterface
import com.paultorres.movies.data.local.MoviesDatabase
import com.paultorres.movies.model.local.MoviesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

//class MoviesRepository {    private val api = MoviesService()
@Singleton
class MoviesRepository @Inject constructor(
    private val api: MoviesAPIInterface,
    private val db: MoviesDatabase
) {
//class MoviesRepository @Inject constructor(private val api: MoviesAPIInterface) {


    //@OptIn(ExperimentalPagingApi::class)
    @ExperimentalPagingApi
    fun getMovies(type_movie: String, type: String): Flow<PagingData<MoviesEntity>> {

        return Pager(
            config = PagingConfig(
                /*pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false*/
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40


            ),
            remoteMediator = MoviesRemoteMediator(
                api, db, type_movie, type, null
            ),
            // pagingSourceFactory = { MoviesPagingSource(api, "movie", "popular", null) }
            pagingSourceFactory = { db.moviesDBInterface().getPagingMovie(type_movie, type) }

        ).flow

    }

    @ExperimentalPagingApi
    fun getSearch(type_movie: String, query: String): Flow<PagingData<MoviesEntity>> {
        return Pager(
            config = PagingConfig(
                /*pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false*/
                //pageSize = 20,
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 40


            ),
            remoteMediator = MoviesRemoteMediator(
                api, db, type_movie, null, query
            ),
            pagingSourceFactory = { db.moviesDBInterface().getSearchPagingMovie(type_movie, query) }

        ).flow
    }

    suspend fun getMovie(movie_type: String, id: Int): MoviesEntity {
        //  val response = api.getMovie(type,id)
        // return response
        //  return api.getMovie(type, id)
        return db.moviesDBInterface().getSearchMovie(movie_type, id)
    }
}