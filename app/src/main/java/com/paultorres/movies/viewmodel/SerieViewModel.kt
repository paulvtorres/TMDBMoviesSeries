package com.paultorres.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.util.POPULAR_TYPE
import com.paultorres.movies.util.SERIE_TYPE
import com.paultorres.movies.util.TOP_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SerieViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    val pop = repository.getMovies(SERIE_TYPE, POPULAR_TYPE).cachedIn(viewModelScope)
    val top = repository.getMovies(SERIE_TYPE, TOP_TYPE).cachedIn(viewModelScope)
}
