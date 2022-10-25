package com.kev.tvguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.repositories.TopRatedMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
private val repository: TopRatedMoviesRepository
) : ViewModel() {

	private var currentMoviesResult: Flow<PagingData<MovieItem>>? = null

	fun fetchPopularMovies() : Flow<PagingData<MovieItem>>{

		val topRatedMovies = repository.fetchTopRatedMovies()
		currentMoviesResult = topRatedMovies
		return topRatedMovies
	}

}