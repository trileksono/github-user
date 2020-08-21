package com.tleksono.githubuser.domain.model

/**
 * Created by trileksono on 18/08/20
 */
data class Result(
    val nextPage: String,
    val lastPage: String,
    val items: List<User>,
    val totalCount: Int
)
