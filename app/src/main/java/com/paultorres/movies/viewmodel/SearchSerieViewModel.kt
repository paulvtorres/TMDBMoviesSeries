package com.paultorres.movies.viewmodel

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.util.SERIE_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SearchSerieViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    companion object {
        private const val DEFAULT_QUERY = ""
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val search = currentQuery.switchMap {
        repository.getSearch(SERIE_TYPE, it).asLiveData().cachedIn(viewModelScope)
    }

    fun searchMovies(query: String) {
        currentQuery.value = query
    }
}




