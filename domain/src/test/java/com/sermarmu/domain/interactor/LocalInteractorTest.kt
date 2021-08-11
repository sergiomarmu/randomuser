@file:Suppress("EXPERIMENTAL_API_USAGE")
package com.sermarmu.domain.interactor

import com.google.common.truth.Truth
import com.sermarmu.domain.interactor.LocalInteractor
import com.sermarmu.domain.interactor.LocalInteractorImpl
import com.sermarmu.domain.repository.LocalRepository
import com.sermarmu.domain.utils.FakeObjectProviderTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class LocalInteractorTest {

    private val localRepository = mockkClass(LocalRepository::class)
    private lateinit var localInteractor: LocalInteractor

    @Before
    fun setUp() {
        localInteractor = LocalInteractorImpl(
            localRepository
        )
    }

    @Test
    fun `should return a Unit when insertAllUsers is called`() = runBlockingTest {
        coEvery {
            localRepository.insertAllUsers(
                listOf(FakeObjectProviderTest.fakeUserModel)
            )
        } returns Unit

        localInteractor.insertAllUsers(
            listOf(FakeObjectProviderTest.fakeUserModel)
        )

        coVerify {
            localRepository.insertAllUsers(
                listOf(FakeObjectProviderTest.fakeUserModel)
            )
        }
    }

    @Test
    fun `should return a list of userModel with one item when retrieveAllUsers is called`() =
        runBlockingTest {

            coEvery { localRepository.retrieveAllUsers() } returns listOf(
                FakeObjectProviderTest.fakeUserModel
            )

            val expectedResult = listOf(FakeObjectProviderTest.fakeUserModel)

            val result = localInteractor.retrieveAllUsers()

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of userModel when findUserByName is called with an arg passed as string`() =
        runBlockingTest {
            val query = "fake"
            coEvery { localRepository.findUserByName(query) } returns listOf(FakeObjectProviderTest.fakeUserModel)

            val expectedResult = listOf(FakeObjectProviderTest.fakeUserModel)

            val result = localInteractor.findUserByName(query)

            Truth.assertThat(result).isEqualTo(expectedResult)
            Truth.assertThat(result).isNotNull()
            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a Unit when clearAllUsers is called`() = runBlockingTest {
        coEvery {
            localRepository.clearAllUsers()
        } returns Unit


        localInteractor.clearAllUsers()

        coVerify {
            localRepository.clearAllUsers()
        }
    }

    @Test
    fun `should return a Unit when deleteUser is called`() = runBlockingTest {
        coEvery {
            localRepository.deleteUser(
                FakeObjectProviderTest.fakeUserModel
            )
        } returns Unit


        localInteractor.deleteUser(
            FakeObjectProviderTest.fakeUserModel
        )

        coVerify {
            localRepository.deleteUser(
                FakeObjectProviderTest.fakeUserModel
            )
        }
    }
}