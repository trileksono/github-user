package com.tleksono.githubuser.data.source.cloud

import com.tleksono.githubuser.data.entity.ResultEntity
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by trileksono on 18/08/20
 */
interface GithubApi {
    fun getUsers(suffixUrl: String): Single<Response<ResultEntity>>
}
