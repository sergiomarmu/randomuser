package com.sermarmu.data.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.sermarmu.data.entity.User
import com.sermarmu.data.source.local.LocalApi
import com.sermarmu.data.source.local.LocalDatabase
import com.sermarmu.data.source.local.LocalSource
import com.sermarmu.data.source.local.LocalSourceImpl
import com.sermarmu.data.utils.FakeObjectProviderAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalSourceTest {

    private lateinit var localApiDao: LocalApi
    private lateinit var localDatabase: LocalDatabase
    private lateinit var localSource: LocalSource

    @Before
    fun setUp() {
        val context = ApplicationProvider
            .getApplicationContext<Context>()

        localDatabase = Room.inMemoryDatabaseBuilder(
            context, LocalDatabase::class.java
        ).build()
        localApiDao = localDatabase.localApi()

        localSource = LocalSourceImpl(
            localApiDao
        )
    }

    @Test
    fun shouldReturnAnListOfUserWithOneItemAfterInsertIt() = runBlocking {
        val fakeUser = FakeObjectProviderAndroidTest.fakeUser

        localSource.insertAllUsers(
            listOf(fakeUser)
        )

        val expectedResult = fakeUser

        val result = localSource
            .retrieveAllUsers()
            .first()

        Truth.assertThat(result.name.first).isEqualTo(expectedResult.name.first)
        Truth.assertThat(result.location.street.name).isEqualTo(expectedResult.location.street.name)
        Truth.assertThat(result.phone).isEqualTo(expectedResult.phone)
    }


    @Test
    fun shouldReturnAnEmptyListOfUserAfterRemoveAnUser() = runBlocking {
        val fakeUser = FakeObjectProviderAndroidTest.fakeUser

        localSource.insertAllUsers(
            listOf(fakeUser)
        )

        localSource
            .deleteUser(fakeUser)

        val expectedResult = emptyList<User>()

        val result = localSource
            .retrieveAllUsers()
            .filter { it.id == fakeUser.id }

        Truth.assertThat(result.isEmpty())
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun shouldReturnAnListOfUserThatMatchWithQueryGiven() = runBlocking {
        val fakeUser = FakeObjectProviderAndroidTest.fakeUser
        val fakeQuery = "fake"

        localSource.insertAllUsers(
            listOf(fakeUser)
        )

        val expectedResult = localSource
            .findUserByName(fakeQuery)

        val result = localSource
            .retrieveAllUsers()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun shouldReturnAnEmptyListOfUserAfterRemovedAllUsers() = runBlocking {
        val fakeUser = FakeObjectProviderAndroidTest.fakeUser

        localSource.insertAllUsers(
            listOf(fakeUser)
        )

        localSource.clearAllUsers()

        val expectedResult = emptyList<User>()

        val result = localSource
            .retrieveAllUsers()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @After
    fun closeDatabase() {
        localDatabase.close()
    }
}