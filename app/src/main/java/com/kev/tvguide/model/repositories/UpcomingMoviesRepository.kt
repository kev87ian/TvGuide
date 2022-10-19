package com.kev.tvguide.model.repositories

import com.kev.tvguide.model.network.MoviesApiService
import javax.inject.Inject

class UpcomingMoviesRepository @Inject constructor(
	private val apiService: MoviesApiService
) {
	suspend fun fetchUpcomingMovies() = apiService.fetchUpcomingMovies()
}