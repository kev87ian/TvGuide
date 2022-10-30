package com.kev.tvguide.repositories

import com.kev.tvguide.BuildConfig
import com.kev.tvguide.network.MoviesApiService
import javax.inject.Inject

class SearchMovieRepository @Inject constructor(
	private val apiService: MoviesApiService
) {

	suspend fun searchMovies(movieName: String) =
		apiService.searchMovie(BuildConfig.API_KEY, movieName)

}