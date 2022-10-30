package com.kev.tvguide.viewmodel

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.tvguide.models.MoviesResponse
import com.kev.tvguide.repositories.SearchMovieRepository
import com.kev.tvguide.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
	private val repository: SearchMovieRepository
) : ViewModel() {

	private val _searchMovieObservable = MutableLiveData<State<MoviesResponse>>()
	val searchMovieObservable : LiveData<State<MoviesResponse>> = _searchMovieObservable

	fun searchForMovies(movieName:String) = viewModelScope.launch {
		_searchMovieObservable.postValue(State.Loading())
		try {
			val result = repository.searchMovies(movieName)
		/*	if (result.isSuccessful){
				_searchMovieObservable.postValue(State.Success(result.body()!!))
			}

			if(result.body()?.movieItems.isNullOrEmpty()){
				_searchMovieObservable.postValue(State.Error("No result(s) found"))
			}*/


			if (result.body()?.movieItems.isNullOrEmpty()){
				_searchMovieObservable.postValue(State.Error("No result(s) found"))
			}
			else{
				_searchMovieObservable.postValue(State.Success(result.body()!!))
			}
		}catch (e:Exception){
			e.printStackTrace()
			when(e){
				is IOException-> _searchMovieObservable.postValue(State.Error("Ensure you have an active internet connection"))
				else -> _searchMovieObservable.postValue(State.Error(e.localizedMessage!!))
			}
		}
	}
}