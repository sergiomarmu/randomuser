package com.sermarmu.randomuser.ui.feature.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sermarmu.core.base.BaseViewModel
import com.sermarmu.core.extension.launchInMain
import com.sermarmu.domain.interactor.usercase.UserCases
import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class UserViewModel : BaseViewModel() {

    // region state
    sealed class UserState {

        sealed class Success : UserState() {
            abstract val users: List<UserModel>

            data class LoadUsers(
                override val users: List<UserModel>
            ) : Success()

            data class LoadNewUsers(
                override val users: List<UserModel>
            ) : Success()

            data class Filter(
                override val users: List<UserModel>
            ) : Success()

            data class UserDeleted(
                override val users: List<UserModel>
            ) : Success()
        }

        data class Failure(
            val error: Throwable
        ) : UserState()

    }
    // endregion state

    // region livedata
    abstract val userStateLiveData: LiveData<UserState>
    // endregion livedata

    // region user action
    /**
     * User request for retrieve more users
     */
    abstract fun onLoadMoreUsersRequest()

    /**
     * User request for search users by name through a query
     */
    abstract fun onQueryTypedRequest(
        query: String
    )

    /**
     * User request for delete an user
     */
    abstract fun onUserRemoveRequest(
        userModel: UserModel
    )
    // endregion user action
}

class UserViewModelImpl(
    private val userCases: UserCases
) : UserViewModel() {

    private val userStateMutableLiveData = MutableLiveData<UserState>()
    override val userStateLiveData: LiveData<UserState>
        get() = userStateMutableLiveData

    init {
        userRequest(
            UserRequestEvent.RetrieveUsers
        )
    }

    // region user request
    override fun onLoadMoreUsersRequest() {
        userRequest(
            UserRequestEvent.RetrieveMoreUsers
        )
    }

    override fun onQueryTypedRequest(
        query: String
    ) {
        userRequest(
            UserRequestEvent.SearchUser(query)
        )
    }

    override fun onUserRemoveRequest(
        userModel: UserModel
    ) {
        userRequest(
            UserRequestEvent.DeleteUser(userModel)
        )
    }
    // endregion user request

    private fun userRequest(
        userRequestEvent: UserRequestEvent
    ) {
        this@UserViewModelImpl
            .viewModelScope.launch {

                when (userRequestEvent) {
                    is UserRequestEvent.RetrieveUsers -> userCases
                        .getUsers()
                        .map {
                            UserState.Success.LoadUsers(it)
                        }
                    is UserRequestEvent.RetrieveMoreUsers -> {
                        userCases
                            .addUsers()

                        userCases
                            .getUsers()
                            .map {
                                UserState.Success.LoadNewUsers(it)
                            }
                    }
                    is UserRequestEvent.SearchUser -> userCases
                        .searchUsers(
                            query = userRequestEvent.query
                        ).map {
                            UserState.Success.Filter(it)
                        }
                    is UserRequestEvent.DeleteUser -> {
                        userCases
                            .deleteUser(
                                userModel = userRequestEvent.userModel
                            )

                        userCases
                            .getUsers()
                            .map {
                                UserState.Success.UserDeleted(it)
                            }
                    }
                }.catch<UserState> { e ->
                    emit(UserState.Failure(e))
                }.collect {
                    launchInMain {
                        userStateMutableLiveData.value = it
                    }
                }
            }
    }
}