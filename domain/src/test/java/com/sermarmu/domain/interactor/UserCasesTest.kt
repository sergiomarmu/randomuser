@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sermarmu.domain.interactor

import com.google.common.truth.Truth
import com.sermarmu.domain.interactor.usercase.*
import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.UserRepository
import com.sermarmu.domain.utils.FakeObjectProviderTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class UserCasesTest {

    private val userRepository = mockkClass(UserRepository::class)

    private val userCases = UserCases(
        GetUsers(userRepository),
        AddUsers(userRepository),
        SearchUsers(userRepository),
        DeleteUser(userRepository)
    )

    @Before
    fun setUp() {
        coEvery { userRepository.retrieveAllAvailableUsers() } returns listOf(FakeObjectProviderTest.fakeUserModel)
    }

    @Test
    fun whenMapping_shouldGetAnUserModel() = runBlockingTest {
        coEvery { userRepository.retrieveAllAvailableUsers() } returns listOf(FakeObjectProviderTest.fakeUserModel)

        val expectedResult = listOf(FakeObjectProviderTest.fakeUserModel)

        val result = userRepository.retrieveAllAvailableUsers()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenRequestingUsers_shouldGetAListFromRepository() = runBlockingTest {
        val expectedResult = flowOf(listOf(FakeObjectProviderTest.fakeUserModel))
            .first()

        val result = userCases.getUsers()
            .first()

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result[0]).isInstanceOf(UserModel::class.java)
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenRequestingAnUserGivenAQuery_shouldGetListFromRepository() = runBlockingTest {
        val query = "fake"

        coEvery { userRepository.findUserByName(query) } returns listOf(FakeObjectProviderTest.fakeUserModel)

        val expectedResult = flowOf(listOf(FakeObjectProviderTest.fakeUserModel))
            .first()

        val result = userCases.searchUsers(query)
            .first()

        Truth.assertThat(result[0].id).isEqualTo(expectedResult[0].id)
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenRequestingAnUserGivenAEmptyQuery_shouldGetListFromRepository() = runBlockingTest {
        val query = String()

        coEvery { userRepository.findUserByName(query) } returns emptyList()

        val expectedResult = flowOf(listOf(FakeObjectProviderTest.fakeUserModel))
            .first()

        val result = userCases.searchUsers(query)
            .first()

        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenRequestingMoreUsers_shouldGetAListFromRepository() = runBlockingTest {
        userCases.addUsers()

        coVerify {
            userRepository.saveUsers(listOf(FakeObjectProviderTest.fakeUserModel))
        }
    }

    @Test
    fun whenDeletingAnUser_shouldGetAListFromRepository() = runBlockingTest {
        userCases.deleteUser(FakeObjectProviderTest.fakeUserModel)

        coVerify {
            userRepository.deleteUser(FakeObjectProviderTest.fakeUserModel)
        }
    }
}