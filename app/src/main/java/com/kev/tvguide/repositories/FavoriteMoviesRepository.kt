package com.kev.tvguide.repositories

import com.kev.tvguide.db.FavoriteMoviesDao
import com.kev.tvguide.models.MovieDetailsResponse
import javax.inject.Inject

class FavoriteMoviesRepository
@Inject constructor(
	private val favoriteMoviesDao: FavoriteMoviesDao
) {
	fun getSavedMovies() = favoriteMoviesDao.getSavedMovies()

	fun checkIfDbIsEmpty() = favoriteMoviesDao.checkIfDbIsEmpty()

	fun deleteMovie(movieDetailsResponse: MovieDetailsResponse) = favoriteMoviesDao.deleteMovie(movieDetailsResponse)
}