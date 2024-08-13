package com.kev.tvguide.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kev.tvguide.data.local.PopularResultEntity
import com.kev.tvguide.data.local.ShowsDB
import com.kev.tvguide.data.mappers.toPopularResult
import com.kev.tvguide.data.remote.APIService
import com.kev.tvguide.data.remote.PopularShowsRemoteMediator
import com.kev.tvguide.data.remote.popular.PopularResponse
import com.kev.tvguide.data.remote.popular.PopularResultDTO
import com.kev.tvguide.domain.PopularResult
import com.kev.tvguide.repository.ShowsRepository
import com.kev.tvguide.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val repository: ShowsRepository,
) : ViewModel() {
//    @OptIn(ExperimentalPagingApi::class)
//    fun getPopularShows(): Flow<PagingData<PopularResult>> = Pager(
//        config = PagingConfig(
//            pageSize = 20,
//            prefetchDistance = 10,
//            initialLoadSize = 20
//        ),
//        pagingSourceFactory = {
//            db.showsDao.pagingSource()
//        },
//        remoteMediator = PopularShowsRemoteMediator(apiService = apiService, db = db)
//    ).flow.map {
//        it.map { entity ->
//            entity.toPopularResult()
//        }
//    }

    val popularShows: Flow<PagingData<PopularResult>> = repository.getPopularShows().map {
        it.map {entity ->
            entity.toPopularResult()
        }
    }.cachedIn(viewModelScope)

}