package com.sermarmu.data.repository

import com.sermarmu.data.entity.User
import com.sermarmu.data.source.network.NetworkSource
import com.sermarmu.data.source.network.io.toUser


interface NetworkRepository {
    suspend fun retrieveUsers(): List<User>
}

class NetworkRepositoryImpl(
    private val networkSource: NetworkSource
) : NetworkRepository {

    override suspend fun retrieveUsers(): List<User> =
        networkSource.retrieveUsers()
            .users
            .toUser()
}