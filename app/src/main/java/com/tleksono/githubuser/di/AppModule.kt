package com.tleksono.githubuser.di

import com.tleksono.githubuser.util.RxScheduler
import com.tleksono.githubuser.util.RxSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by trileksono on 19/08/20
 */
@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideScheduler(): RxScheduler = RxSchedulerImpl()
}
