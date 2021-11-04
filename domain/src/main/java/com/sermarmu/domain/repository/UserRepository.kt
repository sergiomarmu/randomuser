package com.sermarmu.domain.repository

import com.sermarmu.domain.model.UserModel

interface UserRepository {

    suspend fun retrieveNewUsers(): List<UserModel>

    suspend fun saveUsers(
        users: List<UserModel>
    )

    suspend fun retrieveAllAvailableUsers(): List<UserModel>

    suspend fun findUserByName(
        query: String
    ): List<UserModel>

    suspend fun clearAllUsers()

    suspend fun deleteUser(
        user: UserModel
    )
}