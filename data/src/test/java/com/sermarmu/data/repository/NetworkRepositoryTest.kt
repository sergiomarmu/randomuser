@file:Suppress("EXPERIMENTAL_API_USAGE")
package com.sermarmu.data.repository

import com.google.common.truth.Truth
import com.sermarmu.data.entity.toUserModel
import com.sermarmu.data.source.network.NetworkSource
import com.sermarmu.data.utils.FakeObjectProviderTest
import com.sermarmu.domain.repository.NetworkRepository
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class NetworkRepositoryTest {


    private val networkSource = mockkClass(NetworkSource::class)
    private lateinit var networkRepository: NetworkRepository

    @Before
    fun setUp() {
        networkRepository = NetworkRepositoryImpl(
            networkSource
        )
    }

    @Test
    fun `should return a mapped list of user from userOutput list using extension function`() =
        runBlockingTest {
            val expectedResult = listOf(FakeObjectProviderTest.fakeUserModel)

            val result = listOf(FakeObjectProviderTest.fakeUser).toUserModel()

            Truth.assertThat(result).isEqualTo(expectedResult)
        }

    @Test
    fun `should return a list of serOutput when network source is called`() = runBlockingTest {
        coEvery { networkSource.retrieveUsers() } returns listOf(FakeObjectProviderTest.fakeUser)

        val expectedResult =
            networkRepository
                .retrieveUsers()

        val result = listOf(FakeObjectProviderTest.fakeUserModel)

        Truth.assertThat(result).isEqualTo(expectedResult)
    }
}