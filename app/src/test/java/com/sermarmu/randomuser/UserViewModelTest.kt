@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sermarmu.randomuser

import com.google.common.truth.Truth
import com.sermarmu.data.handler.DataException
import com.sermarmu.domain.interactor.usercase.UserCases
import com.sermarmu.randomuser.ui.feature.user.UserViewModel
import com.sermarmu.randomuser.ui.feature.user.UserViewModelImpl
import com.sermarmu.randomuser.utils.FakeObjectProviderTest
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.sermarmu.randomuser.ui.feature.user.UserViewModel.UserState as VM_State

class UserViewModelTest {
/*
    private val userCases = mockkClass(UserCases::class)
    private lateinit var viewModel: UserViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { userCases.getUsers() } returns flowOf(
            listOf(
                FakeObjectProviderTest.fakeUserModel
            )
        )
        viewModel = UserViewModelImpl(userCases)
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

            viewModel.onQueryTypedRequest(query)

            val expectedResult =
                VM_State.Success.Filter(listOf(FakeObjectProviderTest.fakeUserModel))

            val result = viewModel.uiStateFlow
                .first()

            Truth.assertThat(result is VM_State.Success).isTrue()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of userModel through stateFlow with a Success state due to a user delete action`() =
        runBlockingTest {
            coEvery { userInteractor.deleteUserFlow(FakeObjectProviderTest.fakeUserModel) } returns flowOf(
                listOf()
            )

            viewModel.onUserRemoveRequest(FakeObjectProviderTest.fakeUserModel)

            val expectedResult = VM_State.Success.UserDeleted(listOf())

            val result = viewModel.uiStateFlow
                .first()

            Truth.assertThat(result is VM_State.Success).isTrue()
            Truth.assertThat((result as VM_State.Success).users).isEmpty()
        }

    @Test
    fun `should return a list of userModel through stateFlow with a Success state due to a user action`() =
        runBlockingTest {
            coEvery { userInteractor.retrieveUsersFlow() } returns flowOf(
                listOf(
                    FakeObjectProviderTest.fakeUserModel.copy(uuid = "uuid")
                )
            )

            viewModel.onLoadMoreUsersRequest()

            val expectedResult =
                VM_State.Success.LoadNewUsers(listOf(FakeObjectProviderTest.fakeUserModel.copy(uuid = "uuid")))

            val result = viewModel.uiStateFlow
                .first()

            Truth.assertThat(result is VM_State.Success.LoadNewUsers).isTrue()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of userModel through stateFlow with a Success state due to a viewModel creation`() =
        runBlockingTest {
            val expectedResult =
                VM_State.Success.LoadUsers(listOf(FakeObjectProviderTest.fakeUserModel))

            val result = viewModel.uiStateFlow
                .first()

            Truth.assertThat(result is VM_State.Success).isTrue()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of uses through stateFlow with a Failure (no internet connection) state due to a viewModel creation`() =
        runBlockingTest {
            // region arrange
            val localInteractor = mockkClass(LocalInteractor::class)
            val networkInteractor = mockkClass(NetworkInteractor::class)
            coEvery { networkInteractor.retrieveUsers() }.throws(DataException.Network())
            coEvery { localInteractor.retrieveAllUsers() } returns listOf(
                FakeObjectProviderTest.fakeUserModel
            )

            val userInteractor: UserInteractor = UserInteractorImpl(
                localInteractor,
                networkInteractor
            )
            viewModel = UserViewModelImpl(userInteractor)
            // endregion arrange

            // region act
            viewModel.onLoadMoreUsersRequest()
            val result = viewModel.uiStateFlow
                .first()
            // endregion act

            //region assert
            Truth.assertThat(result is VM_State.Success).isFalse()
            Truth.assertThat(result is VM_State.Failure).isTrue()
            Truth.assertThat((result as VM_State.Failure).error)
                .isInstanceOf(DataException.Network::class.java)
            //endregion assert
        }

    @After
    fun afterEnd() {
        Dispatchers.resetMain()
    }*/
}