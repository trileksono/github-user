package com.tleksono.githubuser.domain.repo

import com.tleksono.githubuser.domain.model.Result
import com.tleksono.githubuser.domain.model.State
import io.reactivex.Single

/**
 * Created by trileksono on 18/08/20
 */
interface GithubRepository {

    fun getUsers(suffixUrl: String): Single<State<Result>>
}
