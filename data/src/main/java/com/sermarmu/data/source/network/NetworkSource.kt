package com.sermarmu.data.source.network

import com.sermarmu.core.extension.launchInIO
import com.sermarmu.data.entity.User
import com.sermarmu.data.handler.launchRequest
import com.sermarmu.data.source.network.io.toUser

interface NetworkSource {
    suspend fun retrieveUsers(): List<User>
}

class NetworkSourceImpl(
    private val networkApi: NetworkApi
) : NetworkSource {

    override suspend fun retrieveUsers(): List<User> = launchInIO {
        launchRequest {
            networkApi.retrieveUsers()
        }.users.toUser()
    }

}