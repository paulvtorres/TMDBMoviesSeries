package com.paultorres.movies.viewmodel


import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.paultorres.movies.data.MoviesRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    val pop = repository.getMovies("movie", "popular").cachedIn(viewModelScope)
    val top = repository.getMovies("movie", "top_rated").cachedIn(viewModelScope)
}

