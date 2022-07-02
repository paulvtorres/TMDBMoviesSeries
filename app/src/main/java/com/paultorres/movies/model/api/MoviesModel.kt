package com.paultorres.movies.model.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

import kotlinx.parcelize.Parcelize


data class MoviesModel(

    val id: Int,
    val overview: String?,
    val poster_path: String,
    val title: String,
    val name: String,
    val original_language: String?,
    val release_date: String?,
    val popularity: String?
)