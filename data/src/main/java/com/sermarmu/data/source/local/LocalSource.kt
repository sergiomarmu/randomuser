package com.sermarmu.data.source.local


import com.sermarmu.data.source.local.io.UserDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LocalSource {

    suspend fun insertAllUsers(
        usersDb: List<UserDB>
    )

    suspend fun retrieveAllUsers(): List<UserDB>

    suspend fun findUserWithName(
        search: String
    ): List<UserDB>

    suspend fun clearAllUsers()

    suspend fun deleteUser(
        userDb: UserDB
    )
}

class LocalSourceImpl(
    private val localApi: LocalApi
) : LocalSource {

    override suspend fun insertAllUsers(
        usersDb: List<UserDB>
    ) {
        withContext(Dispatchers.IO) {
            localApi.insertAllUsers(
                users = usersDb
            )
        }
    }

    override suspend fun retrieveAllUsers(): List<UserDB> = withContext(Dispatchers.IO) {
        localApi.retrieveAllUsers()
    }

    override suspend fun findUserWithName(
        search: String
    ): List<UserDB> = withContext(Dispatchers.IO) {
        localApi.findUserWithName(
            "$search%"
        )
    }

    override suspend fun clearAllUsers() {
        withContext(Dispatchers.IO) {
            localApi.clearAllUsers()
        }
    }

    override suspend fun deleteUser(
        userDb: UserDB
    ) {
        withContext(Dispatchers.IO) {
            localApi.deleteUsers(
                user = userDb
            )
        }
    }
}