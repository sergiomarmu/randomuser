package com.sermarmu.data.repository

import com.sermarmu.data.entity.toUser
import com.sermarmu.data.entity.toUserModel
import com.sermarmu.data.source.local.LocalSource
import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.LocalRepository


class LocalRepositoryImpl(
    private val localSource: LocalSource
) : LocalRepository {

    override suspend fun insertAllUsers(
        users: List<UserModel>
    ) {
        localSource.insertAllUsers(
            users = users.toUser()
        )
    }

    override suspend fun retrieveAllUsers(): List<UserModel> =
        localSource.retrieveAllUsers()
            .toUserModel()

    override suspend fun findUserByName(
        query: String
    ): List<UserModel> = localSource.findUserByName(query)
        .toUserModel()

    override suspend fun clearAllUsers() {
        localSource.clearAllUsers()
    }

    override suspend fun deleteUser(
        user: UserModel
    ) {
        localSource.deleteUser(
            user = user.toUser()
        )
    }
}