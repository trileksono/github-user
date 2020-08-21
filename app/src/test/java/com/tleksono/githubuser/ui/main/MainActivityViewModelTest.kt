package com.tleksono.githubuser.ui.main

import androidx.lifecycle.Observer
import com.tleksono.githubuser.domain.model.Result
import com.tleksono.githubuser.domain.model.State
import com.tleksono.githubuser.domain.usecase.GetUserUsecase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

/**
 * Created by trileksono on 19/08/20
 */
class MainActivityViewModelTest {

    private val PARAM = GetUserUsecase.Param("users?q=tes&page=34")
    private var DEFAULT_RESULT = State<Result>(State.Status.SUCCESS)

    private lateinit var viewModel: MainActivityViewModel

    @MockK
    lateinit var getUserUsecase: GetUserUsecase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainActivityViewModel(getUserUsecase)
    }

    private fun createObserver(): Observer<State<Result>> = spyk(Observer { })

    @Test
    fun `should show loading when get data`() {
        val mockObserver = createObserver()
        viewModel.stateLiveData.observeForever(mockObserver)

        every { getUserUsecase.build(PARAM) }.returns(Single.just(DEFAULT_RESULT))


    }

}