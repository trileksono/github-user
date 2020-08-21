package com.tleksono.githubuser.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by trileksono on 18/08/20
 */
data class UserEntity(
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("url")
    val url: String? = null
)