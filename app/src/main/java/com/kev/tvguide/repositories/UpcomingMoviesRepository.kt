package com.kev.tvguide.repositories

import com.kev.tvguide.network.MoviesApiService
import javax.inject.Inject

class UpcomingMoviesRepository @Inject constructor(
	private val apiService: MoviesApiService
) {
	suspend fun fetchUpcomingMovies() = apiService.fetchUpcomingMovies()
}