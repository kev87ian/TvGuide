package com.kev.tvguide.domain

import com.google.gson.annotations.SerializedName

data class PopularResult(
    val backdropPath: String,
    val firstAirDate: String,
    val id: Int,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage : Int
)