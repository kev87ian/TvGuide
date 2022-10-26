package com.kev.tvguide.viewmodel

import androidx.lifecycle.*
import com.bumptech.glide.Glide.init
import com.kev.tvguide.models.MovieCastResponse
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.repositories.MovieDetailsRepository
import com.kev.tvguide.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
	private val repository: MovieDetailsRepository,
	savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val movieId = savedStateHandle.get<Int>("movieID")

	private val _movieDetailsObservable = MutableLiveData<State<MovieDetailsResponse>>()
	val movieDetailsObservable : LiveData<State<MovieDetailsResponse>> = _movieDetailsObservable

	private val _movieCastObservable = MutableLiveData<State<MovieCastResponse>>()
	val movieCastObservable : LiveData<State<MovieCastResponse>> = _movieCastObservable

	fun fetchMovieDetails(movieId:Int) = viewModelScope.launch {
		_movieDetailsObservable.postValue(State.Loading())
		try {
			val result = repository.fetchMovieDetails(movieId)
			_movieDetailsObservable.postValue(State.Success(result.body()!!))

		}catch (e:Exception){
			when(e){
				is IOException -> _movieDetailsObservable.postValue(State.Error("Ensure you have an active internet connection."))
				is HttpException -> _movieDetailsObservable.postValue(State.Error("An unknown error occurred. Please retry"))
				else -> _movieDetailsObservable.postValue(State.Error(e.localizedMessage!!))
			}
		}

	}



	fun fetchMovieCast(movieId: Int) = viewModelScope.launch {
		_movieCastObservable.postValue(State.Loading())
		try {
			val result = repository.fetchMovieCast(movieId)
			if (result.isSuccessful && result.body() != null){
				_movieCastObservable.postValue(State.Success(result.body()!!))
			}

			else if(result.body() == null){
				_movieCastObservable.postValue(State.Error("No cast information available at this moment."))

			}

		}catch (e:Exception){
			when(e){
				is IOException -> _movieCastObservable.postValue(State.Error("No internet connection"))
				else -> _movieCastObservable.postValue(State.Error(e.localizedMessage!!))
			}
		}
	}


	init {
		fetchMovieDetails(movieId!!)
		fetchMovieCast(movieId)
	}


}