package com.kev.tvguide.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import retrofit2.http.GET

@Dao
interface ShowsDao {

    @Upsert
    fun upsertAll(popularShows: List<PopularResultEntity>)

    @Query("select * from popular_shows")
    fun pagingSource(): PagingSource<Int, PopularResultEntity >


    @Query("delete from popular_shows")
    suspend fun clearCache()
}