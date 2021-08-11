@file:Suppress("EXPERIMENTAL_API_USAGE")
package com.sermarmu.data.repository

import com.google.common.truth.Truth
import com.sermarmu.data.entity.toUser
import com.sermarmu.data.source.local.LocalSource
import com.sermarmu.data.utils.FakeObjectProviderTest
import com.sermarmu.domain.repository.LocalRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class LocalRepositoryTest {

    private val localSource = mockkClass(LocalSource::class)
    private lateinit var localRepository: LocalRepository

    @Before
    fun setUp() {
        localRepository = LocalRepositoryImpl(
            localSource
        )
    }

    @Test
    fun `should return a mapped list of users from userMode list using extension function`() =
        runBlockingTest {
            val expectedResult = listOf(FakeObjectProviderTest.fakeUser)

            val result = listOf(FakeObjectProviderTest.fakeUserModel).toUser()

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a Unit when insertAllUsers is called`() = runBlockingTest {
        coEvery {
            localSource.insertAllUsers(
                listOf(FakeObjectProviderTest.fakeUser)
            )
        } returns Unit


        localRepository.insertAllUsers(
            listOf(FakeObjectProviderTest.fakeUserModel)
        )

        coVerify {
            localSource.insertAllUsers(
                listOf(FakeObjectProviderTest.fakeUser)
            )
        }
    }

    @Test
    fun `should return a list of user when retrieveAllUsers is called`() = runBlockingTest {
        coEvery { localSource.retrieveAllUsers() } returns listOf(FakeObjectProviderTest.fakeUser)

        val expectedResult = localRepository
            .retrieveAllUsers()
            .toUser()

        val result = listOf(FakeObjectProviderTest.fakeUser)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should return a list of user when findUserByName is called with an arg passed as string`() = runBlockingTest {
        val query = "fake"
        coEvery { localSource.findUserByName(
            query
        ) } returns listOf(FakeObjectProviderTest.fakeUser)

        val expectedResult = localRepository
            .findUserByName(query)
            .toUser()

        val result = listOf(FakeObjectProviderTest.fakeUser)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should return a Unit when clearAllUsers is called`() = runBlockingTest {
        coEvery {
            localSource.clearAllUsers()
        } returns Unit


        localRepository.clearAllUsers()

        coVerify {
            localSource.clearAllUsers()
        }
    }

    @Test
    fun `should return a Unit when deleteUser is called`() = runBlockingTest {
        coEvery {
            localSource.deleteUser(
                FakeObjectProviderTest.fakeUser
            )
        } returns Unit


        localRepository.deleteUser(
            FakeObjectProviderTest.fakeUserModel
        )

        coVerify {
            localSource.deleteUser(
                FakeObjectProviderTest.fakeUser
            )
        }
    }
}