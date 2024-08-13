package com.kev.tvguide.data.remote.popular


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName



@Keep
data class PopularResultDTO(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,

    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage : Float
)