package com.example.mvvmcoroutines.mappers.network

import com.example.mvvmcoroutines.models.Post
import com.example.mvvmcoroutines.network.entities.PostNetworkEntity
import com.example.mvvmcoroutines.utils.EntityMapper
import javax.inject.Inject

class PostNetworkMapper
    @Inject
    constructor(): EntityMapper<PostNetworkEntity, Post> {

    override fun mapFromEntity(entity: PostNetworkEntity): Post {
        return Post(
            userId = entity.userId,
            id = entity.id,
            title = entity.title,
            body = entity.body
        )
    }

    override fun mapToEntity(domainModel: Post): PostNetworkEntity {
        return PostNetworkEntity(
            userId = domainModel.userId,
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun mapFromPostList(entities: List<PostNetworkEntity>): List<Post> = entities.map { mapFromEntity(it) }
}