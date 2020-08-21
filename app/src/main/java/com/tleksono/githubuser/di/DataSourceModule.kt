package com.tleksono.githubuser.di

import com.tleksono.githubuser.data.source.GithubDataSource
import com.tleksono.githubuser.data.source.cloud.GithubApi
import com.tleksono.githubuser.domain.repo.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by trileksono on 18/08/20
 */
@Module
@InstallIn(ActivityComponent::class)
object DataSourceModule {

    @Provides
    fun provideRepository(githubApi: GithubApi): GithubRepository {
        return GithubDataSource(githubApi)
    }
}
