package com.tleksono.githubuser.domain.usecase

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo

/**
 * Created by trileksono on 18/08/20
 */
abstract class Usecase<Type, in Params> protected constructor(
    private val executeScheduler: Scheduler,
    private val uiScheduler: Scheduler
) {
    protected val disposables = CompositeDisposable()

    fun dispose() = disposables.dispose()

    fun execute(observer: DisposableSingleObserver<Type>, params: Params? = null) {
        get(params).subscribeWith(observer).addTo(disposables)
    }

    abstract fun build(params: Params? = null): Single<Type>

    fun get(params: Params? = null): Single<Type> = build(params)
        .subscribeOn(executeScheduler)
        .observeOn(uiScheduler)
}
