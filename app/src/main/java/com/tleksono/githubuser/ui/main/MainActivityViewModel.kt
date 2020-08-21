package com.tleksono.githubuser.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tleksono.githubuser.BuildConfig
import com.tleksono.githubuser.domain.model.Result
import com.tleksono.githubuser.domain.model.State
import com.tleksono.githubuser.domain.usecase.GetUserUsecase
import com.tleksono.githubuser.util.ErrorUtil
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

/**
 * Created by trileksono on 18/08/20
 */
@ActivityRetainedScoped
class MainActivityViewModel @ViewModelInject constructor(
    private val getUserUsecase: GetUserUsecase
) : ViewModel(), LifecycleObserver {

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 25
    }

    private val mStateLiveData = MutableLiveData<State<Result>>()
    val stateLiveData: LiveData<State<Result>> get() = mStateLiveData

    private val mHasMoreLiveData = MutableLiveData<Boolean>()
    val hashMoreLiveData: LiveData<Boolean> get() = mHasMoreLiveData

    private val compositeDisposable = CompositeDisposable()
    private var url = ""

    fun loadNextData(suffixUrl: String) {
        getUserUsecase.get(GetUserUsecase.Param(suffixUrl))
            .doOnSubscribe { mStateLiveData.postValue(State.loading()) }
            .doOnSuccess { state -> mStateLiveData.postValue(state) }
            .subscribe({ state ->
                state.data?.let {
                    if (it.nextPage != "") mHasMoreLiveData.postValue(true)
                }
            }, {
                mHasMoreLiveData.postValue(true)
                mStateLiveData.postValue(State.error(ErrorUtil.parseError(it)))
            })
            .addTo(compositeDisposable)
    }

    fun searchUser(query: String) {
        url = String.format(BuildConfig.SUFFIX_URL, query, DEFAULT_PAGE, DEFAULT_PAGE_SIZE)
        loadNextData(url)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }


}
