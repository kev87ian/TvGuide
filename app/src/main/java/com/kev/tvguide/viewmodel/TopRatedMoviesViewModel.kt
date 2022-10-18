package com.kev.tvguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.model.network.MoviesApiService
import com.kev.tvguide.model.repositories.TopRatedMoviesRepository
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