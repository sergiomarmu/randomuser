@file:Suppress("EXPERIMENTAL_API_USAGE")
package com.sermarmu.data.source

import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sermarmu.data.source.network.NetworkApi
import com.sermarmu.data.source.network.NetworkSource
import com.sermarmu.data.source.network.NetworkSourceImpl
import com.sermarmu.data.source.network.io.UserListOutput
import com.sermarmu.data.source.network.io.toUser
import com.sermarmu.data.utils.MockJson
import com.sermarmu.data.utils.retrieveApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

private const val fakeRandomUserJsonPath = "random_users_network_response.json"
private const val fakePortServer = 8080
private const val fakeUrl = "/"

@RunWith(MockitoJUnitRunner::class)
class NetworkSourceTest {

    private val fakeServer = MockWebServer()

    private lateinit var networkApi: NetworkApi
    private lateinit var networkSource: NetworkSource
    private lateinit var gson: Gson
    private lateinit var mockJson: MockJson

    /**
     * For access to Dispatchers in unit testing.
     * @see [https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/]
     */
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {

        /**
         * Init server
         */
        fakeServer.start(fakePortServer)

        /**
         * Init Api & necessary elements like Gson
         */
        networkApi = fakeServer.retrieveApi(fakeUrl)

        networkSource = NetworkSourceImpl(networkApi)
        gson = GsonBuilder().create()
        mockJson = MockJson()

        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `should return a api response with one user`() = runBlocking {
        val expectedResult = gson.fromJson(
            mockJson.json(fakeRandomUserJsonPath),
            UserListOutput::class.java
        ).users
            .toUser()

        fakeServer.enqueue(
            MockResponse().setBody(
                mockJson.json(fakeRandomUserJsonPath)
            )
        )

        val result = networkSource
            .retrieveUsers()

        Truth.assertThat(result[0]).isNotNull()
        Truth.assertThat(result[0].uuid).isEqualTo(expectedResult[0].uuid)
        Truth.assertThat(result.size).isEqualTo(expectedResult.size)
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @After
    fun closeServer() {
        // Close server
        Dispatchers.resetMain()
        fakeServer.close()
    }
}