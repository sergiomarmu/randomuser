package com.sermarmu.domain.interactor

import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.NetworkRepository


interface NetworkInteractor {
    suspend fun retrieveUsers(): List<UserModel>
}

class NetworkInteractorImpl(
    private val networkRepository: NetworkRepository
) : NetworkInteractor {

    override suspend fun retrieveUsers(): List<UserModel> =
        networkRepository.retrieveUsers()
            .distinctBy { it.uuid }
}