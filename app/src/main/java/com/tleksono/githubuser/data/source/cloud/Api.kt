package com.tleksono.githubuser.data.source.cloud

import com.tleksono.githubuser.data.entity.ResultEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by trileksono on 18/08/20
 */
interface Api {

    @GET
    fun getUsers(@Url suffixUrll: String): Single<Response<ResultEntity>>
}
