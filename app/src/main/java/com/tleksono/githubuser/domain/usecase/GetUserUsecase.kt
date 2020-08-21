package com.tleksono.githubuser.domain.usecase

import com.tleksono.githubuser.domain.model.Result
import com.tleksono.githubuser.domain.model.State
import com.tleksono.githubuser.domain.repo.GithubRepository
import com.tleksono.githubuser.util.RxScheduler
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by trileksono on 18/08/20
 */
class GetUserUsecase @Inject constructor(
    scheduler: RxScheduler,
    private val repository: GithubRepository
) : Usecase<State<Result>, GetUserUsecase.Param>(scheduler.io(), scheduler.ui()) {

    override fun build(params: Param?): Single<State<Result>> {
        params?.let {
            return repository.getUsers(params.prefix)
        }
        return Single.error(Throwable("Are you forget using param?"))
    }

    data class Param(val prefix: String)

}
