package com.tleksono.githubuser.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by trileksono on 18/08/20
 */
data class ResultEntity(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,
    @SerializedName("items")
    val items: List<UserEntity>? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null
)
