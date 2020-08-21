package com.tleksono.githubuser.data.source

import com.tleksono.githubuser.data.source.cloud.GithubApi
import com.tleksono.githubuser.domain.model.Result
import com.tleksono.githubuser.domain.model.State
import com.tleksono.githubuser.domain.model.User
import com.tleksono.githubuser.domain.repo.GithubRepository
import io.reactivex.Single
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by trileksono on 18/08/20
 */
class GithubDataSource @Inject constructor(
    private val githubApi: GithubApi
) : GithubRepository {
    override fun getUsers(suffixUrl: String): Single<State<Result>> {
        return githubApi.getUsers(suffixUrl)
            .map { response ->
                var nextPage = ""
                var lastPage = ""
                if (response.code() == 200) {
                    response.headers().get("link")?.let { link ->
                        link.split(",").forEach {
                            if (it.contains("next")) {
                                nextPage = it.getUrlFromHeaderLink("next")
                            } else if (it.contains("last")) lastPage = it.getUrlFromHeaderLink("last")
                        }
                    }
                    response.body()?.items?.let { listUser ->
                        State.success(Result(nextPage, lastPage, listUser.map {
                            User(
                                it.avatarUrl.orEmpty(),
                                it.id ?: 1,
                                it.login.orEmpty(),
                                it.url.orEmpty()
                            )
                        }, response.body()?.totalCount ?: 0))
                    }
                } else {
                    throw HttpException(response)
                }
            }
    }

    private fun String.getUrlFromHeaderLink(pattern: String): String {
        return this.split(";")
            .filter { !it.contains(pattern) }
            .map { it.substring(it.indexOf("<") + 1, it.indexOf(">")) }[0]
    }
}
