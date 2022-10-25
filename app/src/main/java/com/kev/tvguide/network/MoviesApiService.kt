package com.kev.tvguide.network

import com.kev.tvguide.BuildConfig
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
	@GET("movie/top_rated")
	suspend fun fetchTopRatedMovies(
		@Query("api_key") api_key: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): Response<MoviesResponse>

	@GET("movie/now_playing")
	suspend fun fetchMoviesNowPlaying(
		@Query("api_key") api_key: String = BuildConfig.API_KEY
	) : Response<MoviesResponse>

	@GET("movie/upcoming")
	suspend fun fetchUpcomingMovies(
		@Query("api_key") api_key: String= BuildConfig.API_KEY
	) : Response<MoviesResponse>

	@GET("movie/{movie_id}")
	suspend fun fetchMovieDetails(
		@Path("movie_id") movieId : Int,
		@Query("api_key") api_key: String = BuildConfig.API_KEY
	) : Response<MovieDetailsResponse>


/*
	@GET("movie/{movie_id}")
	suspend fun fetchSimilarMovies(
		@Path("movie_id") movieId:Int,
		@Query("api_key")api_key: String = BuildConfig.API_KEY
	)


	@GET("movie/{movie_id}/similar")
	suspend fun fetchMovieDetails(
		@Path("movie_id")
		movie_id: Int,
		@Query("api_key")
		api_key: String = BuildConfig.API_KEY
	): Response<List<MovieItem>>


*/


}