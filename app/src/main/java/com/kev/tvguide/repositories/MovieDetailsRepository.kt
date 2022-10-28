package com.kev.tvguide.repositories

import com.kev.tvguide.db.FavoriteMoviesDao
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.network.MoviesApiService
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
	private val apiService: MoviesApiService,
	private val favoriteMoviesDao: FavoriteMoviesDao
) {

	suspend fun fetchMovieDetails(movieId:Int) = apiService.fetchMovieDetails(movieId)

	suspend fun fetchMovieCast(movieId: Int) = apiService.fetchMovieCasts(movieId)

	suspend fun fetchSimilarMovies(movieId: Int) = apiService.fetchSimilarMovies(movieId)

	suspend fun insertMovieIntoDB(movieDetailsResponse: MovieDetailsResponse) = favoriteMoviesDao.saveMovie(movieDetailsResponse)


}