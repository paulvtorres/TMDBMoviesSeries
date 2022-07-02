package com.paultorres.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.paultorres.movies.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SerieViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    val pop = repository.getMovies("tv", "popular").cachedIn(viewModelScope)
    val top = repository.getMovies("tv", "top_rated").cachedIn(viewModelScope)
}
