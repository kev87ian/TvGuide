package com.kev.tvguide.model.repositories

import com.kev.tvguide.model.network.MoviesApiService
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
	private val apiService: MoviesApiService
) {

	suspend fun fetchMovieDetails(movieId:Int) = apiService.fetchMovieDetails(movieId)
}