package com.kev.tvguide.repositories

import com.kev.tvguide.network.MoviesApiService
import javax.inject.Inject


class MoviesNowPlayingRepository @Inject constructor(
	private val moviesApiService: MoviesApiService
){
	suspend fun fetchMoviesNowPlaying() = moviesApiService.fetchMoviesNowPlaying()
}