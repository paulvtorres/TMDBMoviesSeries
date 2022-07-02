package com.paultorres.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.model.local.MoviesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    //val moviesDetailsModel = MutableLiveData<MoviesDetailsModel>()
    val moviesDetailsModel = MutableLiveData<MoviesEntity>()

    fun getMovie(type: String, id: Int) {
        viewModelScope.launch {
            val result = repository.getMovie(type, id)

            //    //  if(!result.isNullOrEmpty()){
            //   //moviesDetailsModel.postValue(result[0])
            moviesDetailsModel.postValue(result)

            //  // isLoading.postValue(false)
        }

    }


}