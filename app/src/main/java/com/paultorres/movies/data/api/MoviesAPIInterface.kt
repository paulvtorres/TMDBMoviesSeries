package com.paultorres.movies.data.api

import com.paultorres.movies.model.api.MoviesResponse
import com.paultorres.movies.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPIInterface {

    @GET("{movie_serie}/{movie_type}?api_key=$API_KEY")
    suspend fun getAllMovies(
        @Path("movie_serie") typemovie: String,
        @Path("movie_type") type: String?,
        @Query("page") pag: Int
    ): MoviesResponse

    @GET("search/{movie_serie}?api_key=$API_KEY")
    suspend fun getSearchMovies(
        @Path("movie_serie") typemovie: String,
        @Query("query") query: String?,
        @Query("page") pag: Int
    ): MoviesResponse
}