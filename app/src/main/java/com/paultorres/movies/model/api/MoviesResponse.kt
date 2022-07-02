package com.paultorres.movies.model.api

import com.paultorres.movies.model.api.MoviesModel

data class MoviesResponse(
    val page: Int,
    val results: List<MoviesModel>
)