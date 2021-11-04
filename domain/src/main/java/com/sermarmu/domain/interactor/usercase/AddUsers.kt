@file:Suppress("EXPERIMENTA_API_USAGE")

package com.sermarmu.domain.interactor.usercase

import com.sermarmu.domain.repository.UserRepository

class AddUsers(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke()  {
        val newUsers = userRepository
            .retrieveNewUsers()
            .distinctBy { it.uuid }

        userRepository.saveUsers(newUsers)
    }
}
