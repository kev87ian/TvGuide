package com.kev.tvguide.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PopularResultEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class ShowsDB : RoomDatabase() {

    abstract val showsDao : ShowsDao
    abstract val remoteKeysDao : RemoteKeysDao
}