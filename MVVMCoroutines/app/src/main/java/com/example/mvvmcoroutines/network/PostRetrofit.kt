package com.example.mvvmcoroutines.network

import com.example.mvvmcoroutines.network.entities.PostNetworkEntity
import retrofit2.http.GET

interface PostRetrofit {

    @GET("posts")
    suspend fun getPosts(): List<PostNetworkEntity>

}