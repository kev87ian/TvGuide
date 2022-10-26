package com.kev.tvguide.repositories

import com.kev.tvguide.network.MoviesApiService
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
	private val apiService: MoviesApiService
) {

	suspend fun fetchMovieDetails(movieId:Int) = apiService.fetchMovieDetails(movieId)

	suspend fun fetchMovieCast(movieId: Int) = apiService.fetchMovieCasts(movieId)

	suspend fun fetchSimilarMovies(movieId: Int) = apiService.fetchSimilarMovies(movieId)
}