package com.kev.tvguide.model.repositories

import com.kev.tvguide.model.network.MoviesApiService
import javax.inject.Inject


class MoviesNowPlayingRepository @Inject constructor(
	private val moviesApiService: MoviesApiService
){
	suspend fun fetchMoviesNowPlaying() = moviesApiService.fetchMoviesNowPlaying()
}