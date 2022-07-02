package com.paultorres.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.paultorres.movies.data.api.MoviesAPIInterface
import com.paultorres.movies.model.api.toMoviesEntity
import com.paultorres.movies.data.local.MoviesDatabase
import com.paultorres.movies.model.local.MoviesEntity

//@OptIn(ExperimentalPagingApi::class)
@ExperimentalPagingApi
class MoviesRemoteMediator
    (
    private val api: MoviesAPIInterface,
    private val db: MoviesDatabase,
    private val type_movie: String,
    private val type: String?,
    private val query: String?
) : RemoteMediator<Int, MoviesEntity>() {

    private var page = 1
    private var endOfPaginationReached = false


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviesEntity>,
    ): MediatorResult {

        page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                state.lastItemOrNull() ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                page + 1
            }
        }

        try {
            //val response = api.getAllMovies(type_movie,type,           page)
            val response = if (query != null) api.getSearchMovies(
                type_movie,
                query, page
            )
            else api.getAllMovies(type_movie, type, page)

            val data = response.toMoviesEntity(type_movie, type)
            //if (data.popularTvList.size < pageSize) {
            /*             if (data.moviesList.size < 21) {
                         endOfPaginationReached = true
                       }
          */
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    if (query == null) {
                        //    db.moviesDBInterface().deleteAllMovies(type_movie, type)
                    }
                }

                db.moviesDBInterface().insertMovies(data.moviesList)
            }


            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            println("ERORRRRRRRRRRRRRRRRRRRRRR")
            return MediatorResult.Error(e)
        }
    }
}