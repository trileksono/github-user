package com.tleksono.githubuser.data.source.cloud

import com.tleksono.githubuser.data.entity.ResultEntity
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by trileksono on 18/08/20
 */
class GithubApiImpl @Inject constructor(private val api: Api) : GithubApi {

    override fun getUsers(suffixUrl: String): Single<Response<ResultEntity>> {
        return api.getUsers(suffixUrl)
    }

}
