package com.sermarmu.data.source.network

import com.sermarmu.data.handler.launchRequest
import com.sermarmu.data.source.network.io.UserListOutput

interface NetworkSource {
    suspend fun retrieveUsers(): UserListOutput
}

class NetworkSourceImpl(
    private val networkApi: NetworkApi
) : NetworkSource {

    override suspend fun retrieveUsers(): UserListOutput = launchRequest {
        networkApi.retrieveUsers()
    }

}