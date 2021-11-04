package com.sermarmu.data.source.network.userrandom

import com.sermarmu.core.extension.launchInIO
import com.sermarmu.data.entity.User
import com.sermarmu.data.handler.launchRequest
import com.sermarmu.data.source.network.userrandom.io.toUser

interface UserRandomSource {
    suspend fun retrieveUsers(): List<User>
}

class UserRandomSourceImpl(
    private val userRandomApi: UserRandomApi
) : UserRandomSource {

    override suspend fun retrieveUsers(): List<User> = launchInIO {
        launchRequest {
            userRandomApi.retrieveUsers()
        }.users.toUser()
    }

}