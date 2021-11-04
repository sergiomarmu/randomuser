package com.sermarmu.data.source.local.room


import com.sermarmu.core.extension.launchInIO
import com.sermarmu.data.entity.User
import com.sermarmu.data.entity.toUserDB
import com.sermarmu.data.source.local.room.io.toUser
import kotlinx.coroutines.flow.Flow

interface UserSource {

    suspend fun insertAllUsers(
        users: List<User>
    )

    suspend fun retrieveAllUsers(): List<User>

    suspend fun findUserByName(
        query: String
    ): List<User>

    suspend fun clearAllUsers()

    suspend fun deleteUser(
        user: User
    )
}

class UserSourceImpl(
    private val userApi: UserApi
) : UserSource {

    override suspend fun insertAllUsers(
        users: List<User>
    ) {
        launchInIO {
            userApi.insertAllUsers(
                users = users.toUserDB()
            )
        }
    }

    override suspend fun retrieveAllUsers(): List<User> = launchInIO {
        userApi.retrieveAllUsers().toUser()
    }

    override suspend fun findUserByName(
        query: String
    ): List<User> = launchInIO {
        userApi.findUserByName(
            "$query%"
        ).toUser()
    }

    override suspend fun clearAllUsers() {
        launchInIO {
            userApi.clearAllUsers()
        }
    }

    override suspend fun deleteUser(
        user: User
    ) {
        launchInIO {
            userApi.deleteUsers(
                user = user.toUserDB()
            )
        }
    }
}