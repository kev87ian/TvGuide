package com.kev.tvguide.model.network

import com.kev.tvguide.BuildConfig
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.model.data.NowPlayingResponse
import com.kev.tvguide.model.data.TopRatedMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
	@GET("movie/top_rated")
	suspend fun fetchTopRatedMovies(
		@Query("api_key") api_key: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): Response<TopRatedMoviesResponse>

	@GET("movie/now_playing")
	suspend fun fetchMoviesNowPlaying(
		@Query("api_key") api_key: String = BuildConfig.API_KEY
	) : Response<List<MovieItem>>
}