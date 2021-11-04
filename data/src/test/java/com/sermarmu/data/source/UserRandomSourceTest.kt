@file:Suppress("EXPERIMENTAL_API_USAGE")
package com.sermarmu.data.source

import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sermarmu.data.source.network.userrandom.UserRandomApi
import com.sermarmu.data.source.network.userrandom.UserRandomSource
import com.sermarmu.data.source.network.userrandom.UserRandomSourceImpl
import com.sermarmu.data.source.network.userrandom.io.UserListOutput
import com.sermarmu.data.source.network.userrandom.io.toUser
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
class UserRandomSourceTest {

    private val fakeServer = MockWebServer()

    private lateinit var userRandomApi: UserRandomApi
    private lateinit var userRandomSource: UserRandomSource
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

        fakeServer.enqueue(
            MockResponse().setBody(
                mockJson.json(fakeRandomUserJsonPath)
            )
        )

        /**
         * Init Api & necessary elements like Gson
         */
        userRandomApi = fakeServer.retrieveApi(fakeUrl)

        userRandomSource = UserRandomSourceImpl(userRandomApi)
        gson = GsonBuilder().create()
        mockJson = MockJson()

        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun whenRequestingNewUsers_shouldGetAnNotNullUserList() = runBlocking {
        val result = userRandomSource
            .retrieveUsers()

        Truth.assertThat(result).isNotNull()
    }

    @Test
    fun whenRequestingNewUsers_shouldGetAnNotEmptyUserList() = runBlocking {
        val result = userRandomSource
            .retrieveUsers()

        Truth.assertThat(result).isNotEmpty()
    }

    @Test
    fun whenRequestingNewUsers_shouldGetAnUserList() = runBlocking {
        val expectedResult = gson.fromJson(
            mockJson.json(fakeRandomUserJsonPath),
            UserListOutput::class.java
        ).users
            .toUser()

        val result = userRandomSource
            .retrieveUsers()

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