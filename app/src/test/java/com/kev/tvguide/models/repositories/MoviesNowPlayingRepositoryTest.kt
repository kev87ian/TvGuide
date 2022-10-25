package com.kev.tvguide.models.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.models.MoviesResponse
import com.kev.tvguide.network.MoviesApiService
import com.kev.tvguide.repositories.MoviesNowPlayingRepository
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MoviesNowPlayingRepositoryTest {
	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	private lateinit var repository: MoviesNowPlayingRepository

	@Mock
	lateinit var moviesApiService: MoviesApiService

	@Before
	fun setup() {
		MockitoAnnotations.openMocks(this)
		repository = MoviesNowPlayingRepository(moviesApiService)
	}


	@Test
	fun `get all movies successfully test`() = runBlocking {
		Mockito.`when`(moviesApiService.fetchMoviesNowPlaying())
			.thenReturn(Response.success(MoviesResponse(1, listOf(), 2, 2)))
		val response = repository.fetchMoviesNowPlaying()

		assertThat(response.code()).isEqualTo(200)
		assertThat(response.body()?.movieItems).isEqualTo(listOf<MovieItem>())
	}


	@Test
	fun `unsuccessful network call returns status 400 test`() = runBlocking {
		val errorResponse = "{\n" +
				"  \"type\": \"error\",\n" +
				"  \"message\": \"What you were looking for isn't here.\"\n" + "}"
		val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())

		Mockito.`when`(moviesApiService.fetchMoviesNowPlaying())
			.thenReturn(Response.error(400, errorResponseBody))

		val response = repository.fetchMoviesNowPlaying()

		assertThat(response.errorBody()).isEqualTo(errorResponseBody)
		assertThat(response.code()).isEqualTo(400)

	}
}