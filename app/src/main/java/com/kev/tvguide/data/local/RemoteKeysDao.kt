package com.kev.tvguide.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RemoteKeysDao {
    @Upsert
    fun insertAll(remoteKeys: List<RemoteKeys>)
    @Query("Select * From remote_key Where movie_id = :id")
    suspend fun getRemoteKeyByMovieID(id: Int): RemoteKeys?

    @Query("Delete From remote_key")
   suspend fun clearRemoteKeys()

    @Query("Select created_at From remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}