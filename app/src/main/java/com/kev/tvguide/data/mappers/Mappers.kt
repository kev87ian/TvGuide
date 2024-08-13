package com.kev.tvguide.data.mappers

import com.kev.tvguide.data.local.PopularResultEntity
import com.kev.tvguide.data.remote.popular.PopularResultDTO
import com.kev.tvguide.domain.PopularResult

fun PopularResultDTO.toPopularResultEntity(): PopularResultEntity {
    return PopularResultEntity(
        backdropPath = backdropPath,
        firstAirDate = firstAirDate,
        id = id,
        name = name,
        originalLanguage = originalLanguage,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        voteAverage = voteAverage
    )
}

fun PopularResultEntity.toPopularResult(): PopularResult{
    return PopularResult(
        backdropPath = backdropPath,
        firstAirDate = firstAirDate,
        id = id,
        name = name,
        originalLanguage = originalLanguage,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        voteAverage = voteAverage
    )
}