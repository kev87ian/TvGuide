package com.kev.tvguide.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.model.repositories.MoviesNowPlayingRepository
import com.kev.tvguide.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MoviesNowPlayingViewModel @Inject constructor(
	private val repository: MoviesNowPlayingRepository
) : ViewModel() {


	private val _moviesNowPlayingObservable = MutableLiveData<Resource<List<MovieItem>>>()
	val moviesNowPlayingObservable : LiveData<Resource<List<MovieItem>>> = _moviesNowPlayingObservable


/*	fun fetchMoviesNowPlaying() = viewModelScope.launch {
		_moviesNowPlayingObservable.postValue(Resource.Loading())
		val response = repository.fetchMoviesNowPlaying()

		try {
			if (response.isSuccessful){
				_moviesNowPlayingObservable.postValue(Resource.Success(response.body()?.movieItems!!))
			}
			else{
				_moviesNowPlayingObservable.postValue(Resource.Error(response.code().toString()))
				Log.e("Error ya", response.code().toString())
			}

		}catch (e:Exception){
			e.printStackTrace()
			when(e){
				is IOException -> _moviesNowPlayingObservable.postValue(Resource.Error("Ensure you have an active internet connection"))
				else -> _moviesNowPlayingObservable.postValue(Resource.Error(e.localizedMessage))
			}
		}
	}*/

	fun fetchMoviesNowPlaying() = viewModelScope.launch {
		_moviesNowPlayingObservable.postValue(Resource.Loading())


		try {
			val response = repository.fetchMoviesNowPlaying()
			_moviesNowPlayingObservable.postValue(Resource.Success(response.body()?.movieItems!!))

		}catch (e:Exception){
			when(e){
				is IOException -> _moviesNowPlayingObservable.postValue(Resource.Error("No internet connection"))
			}
		}



	}

}