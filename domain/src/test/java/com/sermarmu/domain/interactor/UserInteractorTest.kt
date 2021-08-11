@file:Suppress("EXPERIMENTAL_API_USAGE")
package com.sermarmu.domain.interactor

import com.google.common.truth.Truth
import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.utils.FakeObjectProviderTest
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class UserInteractorTest {

    private val localInteractor = mockkClass(LocalInteractor::class)
    private val networkInteractor = mockkClass(NetworkInteractor::class)

    private val userInteractor: UserInteractor = UserInteractorImpl(
        localInteractor,
        networkInteractor
    )

    @Test
    fun `should return a list of userModel from DB`() = runBlockingTest {
        coEvery { localInteractor.retrieveAllUsers() } returns listOf(FakeObjectProviderTest.fakeUserModel)

        val expectedResult = flowOf(listOf(FakeObjectProviderTest.fakeUserModel))
            .first()

        val result = userInteractor.retrieveDBUsersFlow()
            .first()

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result[0]).isInstanceOf(UserModel::class.java)
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should return a list of userModel from DB that match with a given query`() = runBlockingTest {
        val query = "fake"

        coEvery { localInteractor.retrieveAllUsers() } returns listOf(FakeObjectProviderTest.fakeUserModel)
        coEvery { localInteractor.findUserByName(query) } returns listOf(FakeObjectProviderTest.fakeUserModel)

        val expectedResult = flowOf(listOf(FakeObjectProviderTest.fakeUserModel))
            .first()

        val result = userInteractor.retrieveUsersByQueryFlow(query)
            .first()

        Truth.assertThat(result[0].id).isEqualTo(expectedResult[0].id)
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should return a list of userModel from DB that match with a given empty query`() =
        runBlockingTest {
            val query = String()

            coEvery { localInteractor.retrieveAllUsers() } returns listOf(FakeObjectProviderTest.fakeUserModel)
            coEvery { localInteractor.findUserByName(query) } returns emptyList()

            val expectedResult = flowOf(listOf(FakeObjectProviderTest.fakeUserModel))
                .first()

            val result = userInteractor.retrieveUsersByQueryFlow(query)
                .first()

            Truth.assertThat(result).isNotEmpty()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of userModel from API`() = runBlockingTest {
        val anotherFakeUser = FakeObjectProviderTest.fakeUserModel.copy(
            name = UserModel.SurName(
                first = "FooFake",
                last = "BarFake"
            )
        )

        coEvery { networkInteractor.retrieveUsers() } returns listOf(FakeObjectProviderTest.fakeUserModel)
        coEvery { localInteractor.retrieveAllUsers() } returns listOf(anotherFakeUser, FakeObjectProviderTest.fakeUserModel)
        coEvery { localInteractor.insertAllUsers(listOf(FakeObjectProviderTest.fakeUserModel)) } returns Unit

        val expectedResult = listOf(anotherFakeUser, FakeObjectProviderTest.fakeUserModel)

        val result = userInteractor.retrieveUsersFlow()
            .first()

        coVerify {
            localInteractor.insertAllUsers(listOf(FakeObjectProviderTest.fakeUserModel))
        }

        Truth.assertThat(result.size).isEqualTo(expectedResult.size)
        Truth.assertThat(result).isEqualTo(expectedResult)
    }


    @Test
    fun `should return a list of userModel from DB after remove an userModel`() = runBlockingTest {
        coEvery { localInteractor.retrieveAllUsers() } returns emptyList()
        coEvery { localInteractor.deleteUser(FakeObjectProviderTest.fakeUserModel) } returns Unit

        val expectedResult = flowOf(emptyList<UserModel>())
            .first()

        val result = userInteractor.deleteDBUserFlow(FakeObjectProviderTest.fakeUserModel)
            .first()

        coVerify {
            localInteractor.deleteUser(FakeObjectProviderTest.fakeUserModel)
        }

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isEmpty()
        Truth.assertThat(result).isEqualTo(expectedResult)
    }
}