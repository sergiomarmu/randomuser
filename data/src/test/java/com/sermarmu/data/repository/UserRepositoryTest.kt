@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sermarmu.data.repository

import com.google.common.truth.Truth
import com.sermarmu.data.entity.toUser
import com.sermarmu.data.entity.toUserModel
import com.sermarmu.data.source.local.room.UserSource
import com.sermarmu.data.source.network.userrandom.UserRandomSource
import com.sermarmu.data.utils.FakeObjectProviderTest
import com.sermarmu.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private val userNetworkSource = mockkClass(UserRandomSource::class)
    private val userLocalSource = mockkClass(UserSource::class)

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(
            userLocalSource,
            userNetworkSource
        )
    }

    @Test
    fun whenMapping_shouldGetAnUser() = runBlockingTest {
            val expectedResult = listOf(FakeObjectProviderTest.fakeUserModel)

            val result = listOf(FakeObjectProviderTest.fakeUser).toUserModel()

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun whenRequestingNewUsers_shouldGetFromUserRandomNetwork() = runBlockingTest {
        coEvery { userNetworkSource.retrieveUsers() } returns listOf(FakeObjectProviderTest.fakeUser)

        val expectedResult =
            userRepository
                .retrieveNewUsers()

        val result = listOf(FakeObjectProviderTest.fakeUserModel)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenMappingAnRoomUser_shouldGetAnUser() = runBlockingTest {
            val expectedResult = listOf(FakeObjectProviderTest.fakeUser)

            val result = listOf(FakeObjectProviderTest.fakeUserModel).toUser()

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun whenInsertingUsers_shouldCallRoomSource() = runBlockingTest {
        coEvery {
            userLocalSource.insertAllUsers(
                listOf(FakeObjectProviderTest.fakeUser)
            )
        } returns Unit


        userRepository.saveUsers(
            listOf(FakeObjectProviderTest.fakeUserModel)
        )

        coVerify {
            userLocalSource.insertAllUsers(
                listOf(FakeObjectProviderTest.fakeUser)
            )
        }
    }

    @Test
    fun whenRequestingNewUsers_shouldGetFromRoomSource() = runBlockingTest {
        coEvery { userLocalSource.retrieveAllUsers() } returns listOf(FakeObjectProviderTest.fakeUser)

        val expectedResult = userRepository
            .retrieveAllAvailableUsers()
            .toUser()

        val result = listOf(FakeObjectProviderTest.fakeUser)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenSearchingAnUser_shouldGetFromRoomSource() = runBlockingTest {
        val query = "fake"
        coEvery { userLocalSource.findUserByName(
            query
        ) } returns listOf(FakeObjectProviderTest.fakeUser)

        val expectedResult = userRepository
            .findUserByName(query)
            .toUser()

        val result = listOf(FakeObjectProviderTest.fakeUser)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenRemovingAllUsers_shouldCallRoomSource() = runBlockingTest {
        coEvery {
            userLocalSource.clearAllUsers()
        } returns Unit


        userRepository.clearAllUsers()

        coVerify {
            userLocalSource.clearAllUsers()
        }
    }

    @Test
    fun whenRemovingAnUser_shouldCallRoomSource() = runBlockingTest {
        coEvery {
            userLocalSource.deleteUser(
                FakeObjectProviderTest.fakeUser
            )
        } returns Unit


        userRepository.deleteUser(
            FakeObjectProviderTest.fakeUserModel
        )

        coVerify {
            userLocalSource.deleteUser(
                FakeObjectProviderTest.fakeUser
            )
        }
    }
}