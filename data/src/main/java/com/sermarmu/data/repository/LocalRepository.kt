package com.sermarmu.data.repository

import com.sermarmu.data.entity.User
import com.sermarmu.data.entity.toUserDB
import com.sermarmu.data.source.local.LocalSource
import com.sermarmu.data.source.local.io.toUser

interface LocalRepository {

    suspend fun insertAllUsers(
        users: List<User>
    )

    suspend fun retrieveAllUsers(): List<User>

    suspend fun findUserWithName(
        search: String
    ): List<User>

    suspend fun clearAllUsers()

    suspend fun deleteUser(
        user: User
    )
}

class LocalRepositoryImpl(
    private val localSource: LocalSource
) : LocalRepository {

    override suspend fun insertAllUsers(
        users: List<User>
    ) {
        localSource.insertAllUsers(
            usersDb = users.toUserDB()
        )
    }

    override suspend fun retrieveAllUsers(): List<User> =
        localSource.retrieveAllUsers()
            .toUser()

    override suspend fun findUserWithName(
        search: String
    ): List<User> = localSource.findUserWithName(search)
        .toUser()

    override suspend fun clearAllUsers() {
        localSource.clearAllUsers()
    }

    override suspend fun deleteUser(
        user : User
    ) {
        localSource.deleteUser(
            userDb = user.toUserDB()
        )
    }
}