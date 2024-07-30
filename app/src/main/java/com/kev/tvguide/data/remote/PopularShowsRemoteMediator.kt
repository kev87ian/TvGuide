package com.kev.tvguide.data.remote

import android.graphics.Movie
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kev.tvguide.data.local.PopularResultEntity
import com.kev.tvguide.data.local.RemoteKeys
import com.kev.tvguide.data.local.ShowsDB
import com.kev.tvguide.data.mappers.toPopularResultEntity
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PopularShowsRemoteMediator(
    private val apiService: APIService,
    private val db: ShowsDB,
) : RemoteMediator<Int, PopularResultEntity>() {


    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - (db.remoteKeysDao.getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularResultEntity>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys ?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }


        try {
            val apiResponse = apiService.getPopularShows(page = page)

            val shows = apiResponse.results
            db.runInTransaction {

                runBlocking {
                    if (loadType == LoadType.REFRESH) {
                        db.remoteKeysDao.clearRemoteKeys()
                        db.showsDao.clearCache()
                    }
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val remoteKeys = shows.map {
                        RemoteKeys(
                            movieID = it.id,
                            prevKey = prevKey,
                            currentPage = page,
                            nextKey = nextKey
                        )
                    }
                    db.remoteKeysDao.insertAll(remoteKeys)
                    db.showsDao.upsertAll(shows.map {
                        it.toPopularResultEntity()
                    })
                }


            }

            return MediatorResult.Success(endOfPaginationReached = true)

        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PopularResultEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.remoteKeysDao.getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PopularResultEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            db.remoteKeysDao.getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PopularResultEntity>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            db.remoteKeysDao.getRemoteKeyByMovieID(movie.id)
        }
    }
}