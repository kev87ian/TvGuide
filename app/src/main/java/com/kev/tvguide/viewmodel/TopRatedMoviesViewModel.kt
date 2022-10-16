package com.kev.tvguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kev.tvguide.model.network.MoviesApiService
import com.kev.tvguide.model.repositories.TopRatedMoviesPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
	private val moviesApiService: MoviesApiService
) : ViewModel() {

	val listData = Pager(PagingConfig(pageSize = 1)){
		TopRatedMoviesPagingRepository(moviesApiService)
	}.flow.cachedIn(viewModelScope)
}