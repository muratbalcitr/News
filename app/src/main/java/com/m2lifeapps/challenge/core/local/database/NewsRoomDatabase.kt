package com.m2lifeapps.challenge.core.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.m2lifeapps.challenge.core.local.dao.FavoriteDAO
import com.m2lifeapps.challenge.core.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 5,
    exportSchema = false
)

abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun favoriteDAO(): FavoriteDAO
}
