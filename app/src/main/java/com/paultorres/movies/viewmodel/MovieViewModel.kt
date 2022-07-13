package com.paultorres.movies.viewmodel


import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.util.MOVIE_TYPE
import com.paultorres.movies.util.POPULAR_TYPE
import com.paultorres.movies.util.TOP_TYPE

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    val pop = repository.getMovies(MOVIE_TYPE, POPULAR_TYPE).cachedIn(viewModelScope)
    val top = repository.getMovies(MOVIE_TYPE, TOP_TYPE).cachedIn(viewModelScope)
}

