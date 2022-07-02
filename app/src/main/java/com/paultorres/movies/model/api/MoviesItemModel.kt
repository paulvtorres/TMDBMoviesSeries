package com.paultorres.movies.model.api

import com.paultorres.movies.model.local.MoviesEntity

data class MoviesItemModel(
    val page: Int,
    val moviesList: List<MoviesEntity>
)