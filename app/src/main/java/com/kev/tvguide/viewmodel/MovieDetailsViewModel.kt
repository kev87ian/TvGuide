package com.kev.tvguide.viewmodel

import androidx.lifecycle.*
import com.kev.tvguide.model.data.MovieDetailsResponse
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.model.repositories.MovieDetailsRepository
import com.kev.tvguide.utils.Resource
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

	private val _movieDetailsObservable = MutableLiveData<Resource<MovieDetailsResponse>>()
	val movieDetailsObservable : LiveData<Resource<MovieDetailsResponse>> = _movieDetailsObservable

	fun fetchMovieDetails(movieId:Int) = viewModelScope.launch {
		_movieDetailsObservable.postValue(Resource.Loading())
		try {
			val result = repository.fetchMovieDetails(movieId)
			_movieDetailsObservable.postValue(Resource.Success(result.body()!!))

		}catch (e:Exception){
			when(e){
				is IOException -> _movieDetailsObservable.postValue(Resource.Error("Ensure you have an active internet connection."))
				is HttpException -> _movieDetailsObservable.postValue(Resource.Error("An unknown error occurred. Please retry"))
				else -> _movieDetailsObservable.postValue(Resource.Error(e.localizedMessage!!))
			}
		}
	}


	init {
		fetchMovieDetails(movieId!!)
	}
}