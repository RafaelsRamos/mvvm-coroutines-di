package com.example.mvvmcoroutines.cache.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmcoroutines.cache.entities.PostCacheEntity

@Database(entities = [PostCacheEntity::class], version = 1)
abstract class PostDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {

        val DATABASE_NAME: String = "post_db"

    }

}