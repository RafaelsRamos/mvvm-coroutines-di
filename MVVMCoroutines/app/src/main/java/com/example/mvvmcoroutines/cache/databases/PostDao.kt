package com.example.mvvmcoroutines.cache.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmcoroutines.cache.entities.PostCacheEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postCacheEntity: PostCacheEntity): Long

    @Query("SELECT * FROM posts")
    suspend fun get(): List<PostCacheEntity>

}