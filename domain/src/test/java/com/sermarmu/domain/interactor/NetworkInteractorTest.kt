@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.sermarmu.domain.interactor

import com.google.common.truth.Truth
import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.NetworkRepository
import com.sermarmu.domain.utils.FakeObjectProviderTest
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class NetworkInteractorTest {

    private val networkRepository = mockkClass(NetworkRepository::class)

    private lateinit var networkInteractor: NetworkInteractor

    @Before
    fun setUp() {
        networkInteractor = NetworkInteractorImpl(
            networkRepository
        )
    }

    @Test
    fun `should return a list of userModel with one item`() = runBlockingTest {
        coEvery { networkRepository.retrieveUsers() } returns listOf(FakeObjectProviderTest.fakeUserModel)

        val expectedResult = listOf(FakeObjectProviderTest.fakeUserModel)

        val result = networkInteractor.retrieveUsers()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should return a list of userModel with one item although repository retrieve two users equals`() =
        runBlockingTest {
            coEvery { networkRepository.retrieveUsers() } returns listOf(
                FakeObjectProviderTest.fakeUserModel,
                FakeObjectProviderTest.fakeUserModel
            )

            val expectedResult = listOf(FakeObjectProviderTest.fakeUserModel)

            val result = networkInteractor.retrieveUsers()

            Truth.assertThat(result).isNotNull()
            Truth.assertThat(result[0]).isInstanceOf(UserModel::class.java)
            Truth.assertThat(result.size).isEqualTo(expectedResult.size)
            Truth.assertThat(result).isEqualTo(expectedResult)
        }
}