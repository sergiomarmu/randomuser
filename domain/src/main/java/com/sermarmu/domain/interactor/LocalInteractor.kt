package com.sermarmu.domain.interactor

import com.sermarmu.data.repository.LocalRepository
import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.model.toUser
import com.sermarmu.domain.model.toUserModel


interface LocalInteractor {

    suspend fun insertAllUsers(
        user: List<UserModel>
    )

    suspend fun retrieveAllUsers(): List<UserModel>

    suspend fun findUserWithName(
        search: String
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
            users = user.toUser()
        )
    }

    override suspend fun retrieveAllUsers(): List<UserModel> =
        localRepository.retrieveAllUsers().toUserModel()

    override suspend fun findUserWithName(
        search: String
    ): List<UserModel> = localRepository.findUserWithName(search).toUserModel()


    override suspend fun clearAllUsers() {
        localRepository.clearAllUsers()
    }

    override suspend fun deleteUser(
        userModel: UserModel
    ) {
        localRepository.deleteUser(
            user =  userModel.toUser()
        )
    }
}

