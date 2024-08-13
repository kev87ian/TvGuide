package com.kev.tvguide.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kev.tvguide.data.local.PopularResultEntity
import com.kev.tvguide.data.local.ShowsDB
import com.kev.tvguide.data.remote.popular.PopularResponse
import com.kev.tvguide.data.remote.APIService
import com.kev.tvguide.data.remote.PopularShowsRemoteMediator
import com.kev.tvguide.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShowsRepository @Inject constructor(
    private val apiService: APIService,
    private val database: ShowsDB
) {


    @OptIn(ExperimentalPagingApi::class)
    fun getPopularShows(): Flow<PagingData<PopularResultEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = PopularShowsRemoteMediator(apiService, database),
            pagingSourceFactory = { database.showsDao.pagingSource() }
        ).flow
    }


    suspend fun clearCache(){
        database.remoteKeysDao.clearRemoteKeys()
        database.showsDao.clearCache()
    }

}