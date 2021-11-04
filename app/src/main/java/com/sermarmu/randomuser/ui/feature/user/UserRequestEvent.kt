package com.sermarmu.randomuser.ui.feature.user

import com.sermarmu.domain.model.UserModel

sealed class UserRequestEvent {
    object RetrieveUsers : UserRequestEvent()
    object RetrieveMoreUsers : UserRequestEvent()
    data class SearchUser(
        val query: String
    ) : UserRequestEvent()

    data class DeleteUser(
        val userModel: UserModel
    ) : UserRequestEvent()
}