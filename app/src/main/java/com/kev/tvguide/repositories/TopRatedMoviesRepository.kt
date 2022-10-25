package com.kev.tvguide.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kev.tvguide.paging.TopRatedMoviesPagingSource
import com.kev.tvguide.network.MoviesApiService
import javax.inject.Inject

class TopRatedMoviesRepository @Inject constructor(
	private val apiService: MoviesApiService
){
	fun fetchTopRatedMovies() = Pager(
		pagingSourceFactory = { TopRatedMoviesPagingSource(apiService) },
		config = PagingConfig(
		pageSize = 20
		)
	).flow
}

