package com.kev.tvguide.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.tvguide.data.remote.popular.PopularResponse
import com.kev.tvguide.repository.ShowsRepository
import com.kev.tvguide.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val repository: ShowsRepository,
) : ViewModel() {

//    private val _popularShows = MutableStateFlow<Resource<PopularResponse>>(Resource.Loading())
//    val popularShows = _popularShows.asStateFlow()
//
//
//
//    fun fetchPopularShows()  = viewModelScope.launch {
//        _popularShows.value = Resource.Loading()
//        val results = repository.fetchPopularShows()
//        _popularShows.value = results
//    }
//
//
//    init {
//        fetchPopularShows()
//    }

}