package com.sermarmu.data.repository

import com.sermarmu.data.entity.toUser
import com.sermarmu.data.entity.toUserModel
import com.sermarmu.data.source.local.room.UserSource
import com.sermarmu.data.source.network.userrandom.UserRandomSource
import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class UserRepositoryImpl(
    private val userLocalSource: UserSource,
    private val userNetworkSource: UserRandomSource
) : UserRepository {

    override suspend fun retrieveNewUsers(): List<UserModel> =
        userNetworkSource.retrieveUsers()
            .toUserModel()

    override suspend fun saveUsers(
        users: List<UserModel>
    ) {
        userLocalSource.insertAllUsers(
            users = users.toUser()
        )
    }

    override suspend fun retrieveAllAvailableUsers(): List<UserModel> =
        userLocalSource.retrieveAllUsers()
            .toUserModel()


    override suspend fun findUserByName(
        query: String
    ): List<UserModel> = userLocalSource.findUserByName(query)
        .toUserModel()

    override suspend fun clearAllUsers() {
        userLocalSource.clearAllUsers()
    }

    override suspend fun deleteUser(
        user: UserModel
    ) {
        userLocalSource.deleteUser(
            user = user.toUser()
        )
    }
}