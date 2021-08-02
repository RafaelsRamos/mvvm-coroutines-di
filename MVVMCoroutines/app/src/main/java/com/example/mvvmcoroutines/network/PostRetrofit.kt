package com.example.mvvmcoroutines.network

import com.example.mvvmcoroutines.network.entities.PostNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface PostRetrofit {

    @GET("posts")
    suspend fun getPosts(): List<PostNetworkEntity>

    @GET("posts")
    suspend fun getPostsByPage(@Query("_page") pageNumber: Int, @Query("_limit") entriesPerPage: Int): List<PostNetworkEntity>

    @GET("posts")
    suspend fun getPostsByRange(@Query("_start") startId: Int, @Query("_end") endId: Int): List<PostNetworkEntity>

}