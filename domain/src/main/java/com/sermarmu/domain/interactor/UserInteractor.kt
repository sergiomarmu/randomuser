@file:Suppress("EXPERIMENTA_API_USAGE")
package com.sermarmu.domain.interactor

import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface UserInteractor {

    suspend fun retrieveDBUsersFlow(): Flow<List<UserModel>>

    suspend fun retrieveUsersFlow(): Flow<List<UserModel>>

    suspend fun retrieveUsersByQueryFlow(
        query: String
    ): Flow<List<UserModel>>

    suspend fun deleteDBUserFlow(
        userModel: UserModel
    ): Flow<List<UserModel>>
}

class UserInteractorImpl(
    private val localInteractor: LocalInteractor,
    private val networkInteractor: NetworkInteractor
) : UserInteractor {

    override suspend fun retrieveDBUsersFlow(): Flow<List<UserModel>> = flow {
            emit(localInteractor.retrieveAllUsers())
        }

    override suspend fun retrieveUsersFlow(): Flow<List<UserModel>> = flow {
        emit(
            networkInteractor
                .retrieveUsers()
        )
    }.map {
        localInteractor.insertAllUsers(it)
        localInteractor.retrieveAllUsers()
    }

    override suspend fun retrieveUsersByQueryFlow(
        query: String
    ): Flow<List<UserModel>> = flow {
        when {
            query.isEmpty() -> emit(localInteractor.retrieveAllUsers())
            else -> emit(localInteractor.findUserByName(query))
        }
    }

    override suspend fun deleteDBUserFlow(
        userModel: UserModel
    ): Flow<List<UserModel>> = localInteractor.deleteUser(
        userModel = userModel
    ).run {
        retrieveDBUsersFlow()
    }
}
