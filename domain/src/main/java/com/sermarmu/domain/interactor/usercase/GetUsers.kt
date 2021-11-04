@file:Suppress("EXPERIMENTA_API_USAGE")
package com.sermarmu.domain.interactor.usercase

import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetUsers(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<UserModel>> =
        flow { emit(userRepository.retrieveAllAvailableUsers()) }
}
