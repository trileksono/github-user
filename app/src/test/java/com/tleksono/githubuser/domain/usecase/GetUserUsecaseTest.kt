package com.tleksono.githubuser.domain.usecase

import com.tleksono.githubuser.TestScheduler
import com.tleksono.githubuser.domain.model.Result
import com.tleksono.githubuser.domain.model.State
import com.tleksono.githubuser.domain.repo.GithubRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test


/**
 * Created by trileksono on 19/08/20
 */

class GetUserUsecaseTest {
    private val PARAM = GetUserUsecase.Param("users?q=tes&page=34")
    private var DEFAULT_RESULT = State<Result>(State.Status.SUCCESS)

    private lateinit var getUserUsecase: GetUserUsecase

    @SpyK
    var testSchedulers: TestScheduler = TestScheduler()

    @MockK
    lateinit var repository: GithubRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getUserUsecase = spyk(GetUserUsecase(testSchedulers, repository))
    }

    @Test
    fun `should be build and subscribe`() {
        // given
        val testObserver = TestObserver<State<Result>>()
        every { getUserUsecase.build(PARAM) }
            .returns(Single.just(DEFAULT_RESULT))

        // when
        getUserUsecase.build(PARAM).subscribeWith(testObserver)
        // then
        assert(testObserver.hasSubscription()) { true }

        // when
        testObserver.dispose()
        // then
        assert(testObserver.isDisposed)
    }

    @Test
    fun `should error null param`() {
        // given
        val testObserver = TestObserver<State<Result>>()
        every { getUserUsecase.build(null) }.returns(Single.error(Throwable("Are you forget using param?")))

        // when
        getUserUsecase.build(null).subscribeWith(testObserver)

        //then
        testObserver.assertError(Throwable::class.java)
        assert(testObserver.errors()[0].message == "Are you forget using param?")
    }
}