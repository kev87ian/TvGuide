package com.kev.tvguide.model.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.model.network.MoviesApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MoviesNowPlayingRepositoryTest{
	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	private lateinit var repository: MoviesNowPlayingRepository
	@Mock
	lateinit var moviesApiService: MoviesApiService

	@Before
	fun setup(){
		MockitoAnnotations.openMocks(this)
		repository = MoviesNowPlayingRepository(moviesApiService)
	}



	@Test
	fun `get all movies succesfully test `() = runBlocking {
		Mockito.`when`(moviesApiService.fetchMoviesNowPlaying()).thenReturn(Response.success(listOf()))
		val response  = repository.fetchMoviesNowPlaying()

		assertThat(response.code()).isEqualTo(200)
		assertThat(response.body()).isEqualTo(listOf<MovieItem>())
	}


	@Test
	fun `unsuccessful network call returns status 200 test`() = runBlocking {
		val errorResponse = "{\n" +
				"  \"type\": \"error\",\n" +
				"  \"message\": \"What you were looking for isn't here.\"\n" + "}"
		val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())

		Mockito.`when`(moviesApiService.fetchMoviesNowPlaying()).thenReturn(Response.error(400, errorResponseBody))

		val response = repository.fetchMoviesNowPlaying()

		assertThat(response.errorBody()).isEqualTo(errorResponseBody)
		assertThat(response.code()).isEqualTo(400)

	}
}