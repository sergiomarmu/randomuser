package com.sermarmu.data.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.sermarmu.data.entity.User
import com.sermarmu.data.source.local.room.UserApi
import com.sermarmu.data.source.local.room.UserDatabase
import com.sermarmu.data.source.local.room.UserSource
import com.sermarmu.data.source.local.room.UserSourceImpl
import com.sermarmu.data.utils.FakeObjectProviderAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSourceTest {

    private lateinit var userApiDao: UserApi
    private lateinit var userDatabase: UserDatabase
    private lateinit var userSource: UserSource

    private val fakeUser = FakeObjectProviderAndroidTest.fakeUser

    @Before
    fun setUp() {
        val context = ApplicationProvider
            .getApplicationContext<Context>()

        userDatabase = Room.inMemoryDatabaseBuilder(
            context, UserDatabase::class.java
        ).build()
        userApiDao = userDatabase.localApi()

        userSource = UserSourceImpl(
            userApiDao
        )

        runBlocking {
            userSource.insertAllUsers(
                listOf(fakeUser)
            )
        }
    }

    @Test
    fun whenInsertingUsers_shouldGetAnUserList() = runBlocking {
        val expectedResult = fakeUser

        val result = userSource
            .retrieveAllUsers()

        Truth.assertThat(result.size).isEqualTo(1)
        Truth.assertThat(result[0].location.street.name).isEqualTo(expectedResult.location.street.name)
        Truth.assertThat(result[0].phone).isEqualTo(expectedResult.phone)
    }

    @Test
    fun whenRemovingAnUser_shouldGetAnEmptyUserList() = runBlocking {
        userSource
            .deleteUser(fakeUser)

        val expectedResult = emptyList<User>()

        val result = userSource
            .retrieveAllUsers()

        Truth.assertThat(result.isEmpty())
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenSearchingAnUser_shouldGetAnUserListWithSearchedUser() = runBlocking {
        val fakeQuery = "fake"

        val expectedResult = userSource
            .findUserByName(fakeQuery)

        val result = userSource
            .retrieveAllUsers()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun whenDeletingAllUsers_shouldGetAnEmptyUserList() = runBlocking {
        userSource.clearAllUsers()

        val expectedResult = emptyList<User>()

        val result = userSource
            .retrieveAllUsers()

        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @After
    fun closeDatabase() {
        userDatabase.close()
    }
}