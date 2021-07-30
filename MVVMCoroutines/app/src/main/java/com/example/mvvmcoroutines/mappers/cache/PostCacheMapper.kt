package com.example.mvvmcoroutines.mappers.cache

import com.example.mvvmcoroutines.cache.entities.PostCacheEntity
import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.utils.EntityMapper
import javax.inject.Inject

class PostCacheMapper
    @Inject
    constructor(): EntityMapper<PostCacheEntity, Post> {

    override fun mapFromEntity(entity: PostCacheEntity): Post {
        return Post(
            id = entity.id,
            userId = entity.userId,
            title = entity.title,
            body = entity.body
        )
    }

    override fun mapToEntity(domainModel: Post): PostCacheEntity {
        return PostCacheEntity(
            id = domainModel.id,
            userId = domainModel.userId,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun mapFromEntityList(entities: List<PostCacheEntity>) = entities.map { mapFromEntity(it) }
}