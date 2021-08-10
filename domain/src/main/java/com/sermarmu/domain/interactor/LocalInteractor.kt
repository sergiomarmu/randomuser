package com.sermarmu.domain.interactor

import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.LocalRepository

interface LocalInteractor {

    suspend fun insertAllUsers(
        user: List<UserModel>
    )

    suspend fun retrieveAllUsers(): List<UserModel>

    suspend fun findUserByName(
        query: String
    ): List<UserModel>

    suspend fun clearAllUsers()

    suspend fun deleteUser(
        userModel: UserModel
    )
}

class LocalInteractorImpl(
    private val localRepository: LocalRepository
) : LocalInteractor {

    override suspend fun insertAllUsers(
        user: List<UserModel>
    ) {
        localRepository.insertAllUsers(
            users = user
        )
    }

    override suspend fun retrieveAllUsers(): List<UserModel> =
        localRepository.retrieveAllUsers()

    override suspend fun findUserByName(
        query: String
    ): List<UserModel> = localRepository.findUserByName(query)


    override suspend fun clearAllUsers() {
        localRepository.clearAllUsers()
    }

    override suspend fun deleteUser(
        userModel: UserModel
    ) {
        localRepository.deleteUser(
            user =  userModel
        )
    }
}

