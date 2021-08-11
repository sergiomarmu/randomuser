package com.sermarmu.data.source.local


import com.sermarmu.core.extension.launchInIO
import com.sermarmu.data.entity.User
import com.sermarmu.data.entity.toUserDB
import com.sermarmu.data.source.local.io.toUser

interface LocalSource {

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

class LocalSourceImpl(
    private val localApi: LocalApi
) : LocalSource {

    override suspend fun insertAllUsers(
        users: List<User>
    ) {
        launchInIO {
            localApi.insertAllUsers(
                users = users.toUserDB()
            )
        }
    }

    override suspend fun retrieveAllUsers(): List<User> = launchInIO {
        localApi.retrieveAllUsers().toUser()
    }

    override suspend fun findUserByName(
        query: String
    ): List<User> = launchInIO {
        localApi.findUserByName(
            "$query%"
        ).toUser()
    }

    override suspend fun clearAllUsers() {
        launchInIO {
            localApi.clearAllUsers()
        }
    }

    override suspend fun deleteUser(
        user: User
    ) {
        launchInIO {
            localApi.deleteUsers(
                user = user.toUserDB()
            )
        }
    }
}