package com.tleksono.githubuser

import com.tleksono.githubuser.util.RxScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by trileksono on 19/08/20
 */
class TestScheduler : RxScheduler {
    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}