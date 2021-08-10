package com.sermarmu.data.repository

import com.sermarmu.data.entity.toUserModel
import com.sermarmu.data.source.network.NetworkSource
import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.NetworkRepository


class NetworkRepositoryImpl(
    private val networkSource: NetworkSource
) : NetworkRepository {

    override suspend fun retrieveUsers(): List<UserModel> =
        networkSource.retrieveUsers()
            .toUserModel()
}