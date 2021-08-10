@file:Suppress("EXPERIMENTAL_API_USAGE")
package com.sermarmu.randomuser

import com.google.common.truth.Truth
import com.sermarmu.domain.interactor.UserInteractor
import com.sermarmu.randomuser.ui.feature.user.UserViewModel
import com.sermarmu.randomuser.ui.feature.user.UserViewModelImpl
import com.sermarmu.randomuser.utils.FakeObjectProviderTest
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.sermarmu.randomuser.ui.feature.user.UserViewModel.UserState as VM_State

class UserViewModelTest {

    private val userInteractor = mockkClass(UserInteractor::class)
    private lateinit var viewModel: UserViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { userInteractor.retrieveDBUsersFlow() } returns flowOf(
            listOf(
                FakeObjectProviderTest.fakeUserModel
            )
        )
        viewModel = UserViewModelImpl(userInteractor)
    }

    @Test
    fun `should return a list of userModel through stateFlow with a Success state due to user filtering action`() =
        runBlockingTest {
            val query = "fake"
            coEvery { userInteractor.retrieveDBUsersFlow() } returns flowOf(
                listOf(
                    FakeObjectProviderTest.fakeUserModel
                )
            )
            coEvery { userInteractor.retrieveUsersByQueryFlow(query) } returns flowOf(
                listOf(
                    FakeObjectProviderTest.fakeUserModel
                )
            )

            viewModel.onQueryTypedAction(query)

            val expectedResult = VM_State.Success(listOf(FakeObjectProviderTest.fakeUserModel))

            val result = viewModel.uiStateSharedFlow
                .first()

            Truth.assertThat(result is VM_State.Success).isTrue()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of userModel through stateFlow with a Success state due to a user delete action`() =
        runBlockingTest {
            coEvery { userInteractor.deleteDBUserFlow(FakeObjectProviderTest.fakeUserModel) } returns flowOf(
                listOf()
            )

            viewModel.onUserRemoveAction(FakeObjectProviderTest.fakeUserModel)

            val expectedResult = VM_State.Success(listOf())

            val result = viewModel.uiStateSharedFlow
                .first()

            Truth.assertThat(result is VM_State.Success).isTrue()
            Truth.assertThat((result as VM_State.Success).users).isEmpty()
        }

    @Test
    fun `should return a list of userModel through stateFlow with a Success state due to a user action`() =
        runBlockingTest {
            coEvery { userInteractor.retrieveUsersFlow() } returns flowOf(
                listOf(
                    FakeObjectProviderTest.fakeUserModel.copy( uuid = "uuid")
                )
            )

            viewModel.onLoadMoreUsersAction()

            val expectedResult = VM_State.Success(listOf(FakeObjectProviderTest.fakeUserModel.copy( uuid = "uuid")))

            val result = viewModel.uiStateSharedFlow
                .first()

            Truth.assertThat(result is VM_State.Success).isTrue()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of userModel through stateFlow with a Success state due to a viewModel creation`() =
        runBlockingTest {
            val expectedResult = VM_State.Success(listOf(FakeObjectProviderTest.fakeUserModel))

            val result = viewModel.uiStateSharedFlow
                .first()

            Truth.assertThat(result is VM_State.Success).isTrue()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @After
    fun afterEnd() {
        Dispatchers.resetMain()
    }
}