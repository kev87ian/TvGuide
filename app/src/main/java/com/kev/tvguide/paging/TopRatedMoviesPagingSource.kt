package com.kev.tvguide.paging

import androidx.paging.PagingSource
import com.kev.tvguide.BuildConfig
import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.network.MoviesApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class TopRatedMoviesPagingSource @Inject constructor(
	private val apiService: MoviesApiService
) : PagingSource<Int, MovieItem>() {


	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {

		return try {
			val currentPage = params.key ?: 1
			val response = apiService.fetchTopRatedMovies(BuildConfig.API_KEY, currentPage)
			val data = response.body()?.movieItems ?: emptyList()
			val maximumPages = response.body()?.totalPages

			val responseData = mutableListOf<MovieItem>()
			responseData.addAll(data)
			LoadResult.Page(
				data = responseData,
				prevKey = if (currentPage == 1) null else -1,
				nextKey = if (maximumPages!! >= currentPage) currentPage.plus(1) else 500
			)


		} catch (e: Exception) {
			e.printStackTrace()
			LoadResult.Error(e)

		} catch (e: IOException) {
			//IO excption for network failures
			e.printStackTrace()
			LoadResult.Error(e)
		} catch (e: HttpException) {
			// HttpException for any non-2xx http status codes
			e.printStackTrace()
			LoadResult.Error(e)
		}


	}

}