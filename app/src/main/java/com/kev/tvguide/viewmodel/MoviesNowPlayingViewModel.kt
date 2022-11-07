package com.kev.tvguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.repositories.MoviesNowPlayingRepository
import com.kev.tvguide.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MoviesNowPlayingViewModel @Inject constructor(
	private val repository: MoviesNowPlayingRepository
) : ViewModel() {


	private val _moviesNowPlayingObservable = MutableLiveData<State<List<MovieItem>>>()
	val moviesNowPlayingObservable : LiveData<State<List<MovieItem>>> = _moviesNowPlayingObservable


	fun fetchMoviesNowPlaying() = viewModelScope.launch {
		_moviesNowPlayingObservable.postValue(State.Loading())


		try {
			val response = repository.fetchMoviesNowPlaying()
			_moviesNowPlayingObservable.postValue(State.Success(response.body()?.movieItems!!))

		}catch (e:Exception){
			e.printStackTrace()
			when(e){
				is IOException -> _moviesNowPlayingObservable.postValue(State.Error("Ensure you have an active internet connection."))
				is HttpException -> _moviesNowPlayingObservable.postValue(State.Error("An unknown error occurred. Please retry"))
				else -> _moviesNowPlayingObservable.postValue(State.Error(e.localizedMessage!!))
			}
		}



	}

}