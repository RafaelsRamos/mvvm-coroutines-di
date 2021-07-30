package com.example.mvvmcoroutines.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostNetworkEntity(

    @SerializedName("id")
    var id: Int,

    @SerializedName("userId")
    var userId: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("body")
    var body: String

)