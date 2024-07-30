package com.kev.tvguide.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_shows")
data class PopularResultEntity(
    val backdropPath: String,
    val firstAirDate: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage : Int
)