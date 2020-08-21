package com.tleksono.githubuser.util

import io.reactivex.Scheduler

/**
 * Created by trileksono on 19/08/20
 */
interface RxScheduler {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}
