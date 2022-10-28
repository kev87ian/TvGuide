package com.kev.tvguide.viewmodel

import androidx.lifecycle.ViewModel
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.repositories.FavoriteMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
	private val repository: FavoriteMoviesRepository
) : ViewModel() {

	fun getSavedMovies() = repository.getSavedMovies()
	fun countRecords() = repository.checkIfDbIsEmpty()
	fun deleteMovie(movieDetailsResponse: MovieDetailsResponse) = repository.deleteMovie(movieDetailsResponse)
}