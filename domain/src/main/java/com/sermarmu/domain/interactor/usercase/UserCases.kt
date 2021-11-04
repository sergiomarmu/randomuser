package com.sermarmu.domain.interactor.usercase

data class UserCases(
    val getUsers: GetUsers,
    val addUsers: AddUsers,
    val searchUsers: SearchUsers,
    val deleteUser: DeleteUser
)