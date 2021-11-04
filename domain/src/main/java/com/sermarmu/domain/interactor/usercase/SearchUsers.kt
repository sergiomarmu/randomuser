@file:Suppress("EXPERIMENTA_API_USAGE")

package com.sermarmu.domain.interactor.usercase

import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchUsers(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        query: String
    ): Flow<List<UserModel>> = flow {
        emit(
            when {
                query.isEmpty() -> userRepository.retrieveAllAvailableUsers()
                else -> userRepository.findUserByName(query)
            }
        )
    }
}
