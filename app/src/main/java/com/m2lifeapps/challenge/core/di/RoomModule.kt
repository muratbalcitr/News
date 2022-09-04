package com.m2lifeapps.challenge.core.di

import android.content.Context
import androidx.room.Room
import com.m2lifeapps.challenge.core.local.dao.FavoriteDAO
import com.m2lifeapps.challenge.core.local.database.NewsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NewsRoomDatabase::class.java,
        "news.db"
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun favoriteDAO(db: NewsRoomDatabase): FavoriteDAO =
        db.favoriteDAO()
}
