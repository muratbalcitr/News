package com.m2lifeapps.challenge.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.m2lifeapps.challenge.core.local.entity.FavoriteEntity

@Dao
interface FavoriteDAO {

    @Query("SELECT * FROM entity_favorite")
    suspend fun getAllFavorite(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM entity_favorite WHERE url = :url")
    suspend fun deleteById(url: String)
}
