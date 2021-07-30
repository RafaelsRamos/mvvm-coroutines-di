package com.example.mvvmcoroutines.di

import com.example.mvvmcoroutines.cache.databases.PostDao
import com.example.mvvmcoroutines.mappers.cache.PostCacheMapper
import com.example.mvvmcoroutines.mappers.network.PostNetworkMapper
import com.example.mvvmcoroutines.network.PostRetrofit
import com.example.mvvmcoroutines.network.repos.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        postDao: PostDao,
        retrofit: PostRetrofit,
        cacheMapper: PostCacheMapper,
        networkMapper: PostNetworkMapper
    ): MainRepository {
        return MainRepository(postDao, retrofit, cacheMapper, networkMapper)
    }

}