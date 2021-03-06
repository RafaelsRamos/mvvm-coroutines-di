package com.example.mvvmcoroutines.network.repos

import com.example.mvvmcoroutines.cache.databases.PostDao
import com.example.mvvmcoroutines.mappers.cache.PostCacheMapper
import com.example.mvvmcoroutines.mappers.network.PostNetworkMapper
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.network.PostRetrofit
import com.example.mvvmcoroutines.network.entities.PostNetworkEntity
import com.example.mvvmcoroutines.utils.DataState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
    constructor(
        private val postDao: PostDao,
        private val postRetrofit: PostRetrofit,
        private val postCacheMapper: PostCacheMapper,
        private val postNetworkMapper: PostNetworkMapper
    ) {

    suspend fun getPostsByPage(page: Int, entriesPerPage: Int): Flow<DataState<List<Post>>> {
        return coroutineScope {
            fetchAndStorePosts { postRetrofit.getPostsByPage(page, entriesPerPage) }
        }
    }

    suspend fun getPostsByRange(start: Int, end: Int): Flow<DataState<List<Post>>> {
        return coroutineScope {
            fetchAndStorePosts { postRetrofit.getPostsByRange(start, end) }
        }
    }

    private suspend fun fetchAndStorePosts(getPostsFunc: suspend () -> List<PostNetworkEntity>) = flow {
        emit(DataState.Loading)

        //TODO("remove below")
        delay(2000)

        try {
            val networkPosts = getPostsFunc()
            val posts = postNetworkMapper.mapFromPostList(networkPosts)

            for (post in posts) {
                postDao.insert(postCacheMapper.mapToEntity(post))
            }
            val cachedPosts = postDao.get()
            emit(DataState.Success(postCacheMapper.mapFromEntityList(cachedPosts)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun fetchCachedPosts(): Flow<DataState<List<Post>>> = flow {
        try {
            val cachedPosts = postDao.get()
            emit(DataState.Success(postCacheMapper.mapFromEntityList(cachedPosts)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}