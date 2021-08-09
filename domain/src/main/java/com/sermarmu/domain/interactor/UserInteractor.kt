package com.sermarmu.domain.interactor

import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserInteractor {
    suspend fun retrieveUsersFlow(): Flow<List<UserModel>>
}

@ExperimentalCoroutinesApi
class UserInteractorImpl(
    private val networkInteractor: NetworkInteractor
) : UserInteractor {


    override suspend fun retrieveUsersFlow(): Flow<List<UserModel>> = flow {
        emit(
            networkInteractor
                .retrieveUsers()
        )
    }
}
