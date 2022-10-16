package com.kev.tvguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.model.data.TopRatedMoviesResponse
import com.kev.tvguide.model.repositories.MoviesNowPlayingRepository
import com.kev.tvguide.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesNowPlayingViewModel @Inject constructor(
	private val repository: MoviesNowPlayingRepository
) : ViewModel() {


	private val _moviesNowPlayingObservable = MutableLiveData<Resource<List<MovieItem>>>()
	val moviesNowPlayingObservable : LiveData<Resource<List<MovieItem>>> = _moviesNowPlayingObservable


	fun fetchMoviesNowPlaying() = viewModelScope.launch {
		_moviesNowPlayingObservable.postValue(Resource.Loading())
		val response = repository.fetchMoviesNowPlaying()
		if (response.isSuccessful){
			_moviesNowPlayingObservable.postValue(Resource.Success(response.body()!!))
		}

		else{
			_moviesNowPlayingObservable.postValue(Resource.Error(response.errorBody().toString()))
		}
	}


}