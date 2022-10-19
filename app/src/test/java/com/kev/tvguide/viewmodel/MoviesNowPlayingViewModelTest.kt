package com.kev.tvguide.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kev.tvguide.getOrAwaitValue
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.model.data.MoviesResponse
import com.kev.tvguide.model.network.MoviesApiService
import com.kev.tvguide.model.repositories.MoviesNowPlayingRepository
import com.kev.tvguide.utils.Resource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
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
@ExperimentalCoroutinesApi
class MoviesNowPlayingViewModelTest {
	private val testDispatcher = UnconfinedTestDispatcher()
	lateinit var viewModel: MoviesNowPlayingViewModel
	lateinit var repository: MoviesNowPlayingRepository

	@Mock
	lateinit var moviesApiService: MoviesApiService

	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@Before
	fun setup() {
		MockitoAnnotations.openMocks(this)
		Dispatchers.setMain(testDispatcher)
		repository = MoviesNowPlayingRepository(moviesApiService)
		viewModel = MoviesNowPlayingViewModel(repository)

	}

	@Test
	fun `get all movies currently playing`() = runBlocking {
		Mockito.`when`(repository.fetchMoviesNowPlaying())
			.thenReturn(Response.success(MoviesResponse(1, listOf<MovieItem>(), 2, 2)))
		viewModel.fetchMoviesNowPlaying()
		val result = viewModel.moviesNowPlayingObservable.getOrAwaitValue()

		assertThat(result.data).isEqualTo(listOf<MovieItem>())
	}


}