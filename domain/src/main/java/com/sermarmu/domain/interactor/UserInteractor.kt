package com.sermarmu.domain.interactor

import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface UserInteractor {

    suspend fun retrieveDBUsersFlow(): Flow<List<UserModel>>

    suspend fun retrieveUsersFlow(): Flow<List<UserModel>>

    suspend fun retrieveUsersWithQueryFlow(
        query: String
    ): Flow<List<UserModel>>

    suspend fun deleteDBUser(
        userModel: UserModel
    ): Flow<List<UserModel>>
}

@ExperimentalCoroutinesApi
class UserInteractorImpl(
    private val localInteractor: LocalInteractor,
    private val networkInteractor: NetworkInteractor
) : UserInteractor {

    override suspend fun retrieveDBUsersFlow(): Flow<List<UserModel>> =
        flowOf(localInteractor.retrieveAllUsers())

    override suspend fun retrieveUsersFlow(): Flow<List<UserModel>> = flow {
        emit(
            networkInteractor
                .retrieveUsers()
        )
    }.map {
        localInteractor.insertAllUsers(it)
        localInteractor.retrieveAllUsers()
    }

    override suspend fun retrieveUsersWithQueryFlow(
        query: String
    ): Flow<List<UserModel>> = flowOf(
        when {
            query.isEmpty() -> localInteractor.retrieveAllUsers()
            else -> localInteractor.findUserWithName(query)
        }
    )

    override suspend fun deleteDBUser(
        userModel: UserModel
    ): Flow<List<UserModel>> = localInteractor.deleteUser(
        userModel = userModel
    ).run {
        retrieveDBUsersFlow()
    }
}
