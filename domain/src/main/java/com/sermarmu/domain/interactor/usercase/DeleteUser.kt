@file:Suppress("EXPERIMENTA_API_USAGE")

package com.sermarmu.domain.interactor.usercase

import com.sermarmu.domain.model.UserModel
import com.sermarmu.domain.repository.UserRepository

class DeleteUser(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        userModel: UserModel
    ) = userRepository.deleteUser(
        user = userModel
    )
}
