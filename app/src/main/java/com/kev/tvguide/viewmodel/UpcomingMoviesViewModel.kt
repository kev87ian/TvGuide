package com.kev.tvguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.repositories.UpcomingMoviesRepository
import com.kev.tvguide.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
	private val repository: UpcomingMoviesRepository
) : ViewModel() {

	private var _upcomingMoviesObservable = MutableLiveData<State<List<MovieItem>>>()
	val upcomingMoviesObservable : LiveData<State<List<MovieItem>>> = _upcomingMoviesObservable


	fun fetchUpcomingMovies() = viewModelScope.launch {
		_upcomingMoviesObservable.postValue(State.Loading())

		try {
			val result = repository.fetchUpcomingMovies()
			_upcomingMoviesObservable.postValue(State.Success(result.body()?.movieItems!!))
		}catch (e:Exception){
				when(e){
					is IOException -> _upcomingMoviesObservable.postValue(State.Error("Ensure you have an active internet connection."))
					is HttpException -> _upcomingMoviesObservable.postValue(State.Error("We could not reach servers, please retry."))
					else -> _upcomingMoviesObservable.postValue(State.Error(e.localizedMessage!!))
				}
		}
	}
}