package com.example.mvvmcoroutines.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmcoroutines.cache.databases.PostDao
import com.example.mvvmcoroutines.cache.databases.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun providePostDb(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            PostDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDAO(postDatabase: PostDatabase): PostDao = postDatabase.postDao()

}