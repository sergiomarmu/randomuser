package com.sermarmu.domain.repository

import com.sermarmu.domain.model.UserModel

interface LocalRepository {

    suspend fun insertAllUsers(
        users: List<UserModel>
    )

    suspend fun retrieveAllUsers(): List<UserModel>

    suspend fun findUserByName(
        query: String
    ): List<UserModel>

    suspend fun clearAllUsers()

    suspend fun deleteUser(
        user: UserModel
    )
}