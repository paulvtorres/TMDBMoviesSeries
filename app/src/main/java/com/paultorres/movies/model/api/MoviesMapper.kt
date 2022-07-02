package com.paultorres.movies.model.api

import com.paultorres.movies.model.local.MoviesEntity

fun MoviesResponse.toMoviesEntity(movie_type: String, type: String?): MoviesItemModel =
    with(this) {
        MoviesItemModel(
            page = page,
            moviesList = results.map {
                MoviesEntity(
                    it.id ?: -1,
                    it.overview ?: "",
                    it.poster_path ?: "",
                    if (it.title == null) it.name else it.title,
                    it.original_language ?: "",
                    it.release_date ?: "",
                    it.popularity ?: "",

                    movie_type,
                    type ?: ""

                )

            } ?: arrayListOf()
        )
    }
