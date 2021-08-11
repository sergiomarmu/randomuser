package com.sermarmu.randomuser.ui.feature.user

import androidx.lifecycle.viewModelScope
import com.sermarmu.core.base.BaseViewModel
import com.sermarmu.core.extension.launchInMain
import com.sermarmu.domain.interactor.UserInteractor
import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class UserViewModel : BaseViewModel() {

    // region state
    sealed class UserState {

        object Idle : UserState()

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

    // region shared flow
    abstract val uiStateFlow: StateFlow<UserState>
    // endregion shared flow

    // region user action
    /**
     * User request for retrieve more users
     */
    abstract fun onLoadMoreUsersAction()

    /**
     * User request for search users by name through a query
     */
    abstract fun onQueryTypedAction(
        query: String
    )

    /**
     * User request for delete an user
     */
    abstract fun onUserRemoveAction(
        userModel: UserModel
    )
    // endregion user action
}

class UserViewModelImpl(
    private val userInteractor: UserInteractor
) : UserViewModel() {

    private val uiStateMutableStateFlow =
        MutableStateFlow<UserState>(UserState.Idle)
    override val uiStateFlow: StateFlow<UserState> =
        uiStateMutableStateFlow

    init {
        this@UserViewModelImpl
            .retrieveUsers()
    }

    // region userAction
    private var onLoadMoreUsersActionJob: Job? = null
    override fun onLoadMoreUsersAction() {
        val oldJob = onLoadMoreUsersActionJob
        onLoadMoreUsersActionJob = this@UserViewModelImpl
            .viewModelScope.launch {
                oldJob?.cancelAndJoin()

                userInteractor
                    .retrieveUsersFlow()
                    .map {
                        UserState.Success.LoadNewUsers(it)
                    }.catch<UserState> { e ->
                        emit(UserState.Failure(e))
                    }.collect {
                        launchInMain {
                            uiStateMutableStateFlow.emit(it)
                        }
                    }
            }
    }

    private var onQueryTypedActionJob: Job? = null
    override fun onQueryTypedAction(
        query: String
    ) {
        val oldJob = onQueryTypedActionJob
        onQueryTypedActionJob = this@UserViewModelImpl
            .viewModelScope.launch {
                oldJob?.cancelAndJoin()

                userInteractor
                    .retrieveUsersByQueryFlow(
                        query = query
                    ).map {
                        UserState.Success.Filter(it)
                    }.catch<UserState> { e ->
                        emit(UserState.Failure(e))
                    }.collect {
                        launchInMain {
                            uiStateMutableStateFlow.emit(it)
                        }
                    }
            }
    }

    private var onUserRemoveActionJob: Job? = null
    override fun onUserRemoveAction(
        userModel: UserModel
    ) {
        val oldJob = onUserRemoveActionJob
        onUserRemoveActionJob = this@UserViewModelImpl
            .viewModelScope.launch {
                oldJob?.cancelAndJoin()

                userInteractor
                    .deleteDBUserFlow(
                        userModel = userModel
                    ).map {
                        UserState.Success.UserDeleted(it)
                    }.catch<UserState> { e ->
                        emit(UserState.Failure(e))
                    }.collect {
                        launchInMain {
                            uiStateMutableStateFlow.emit(it)
                        }
                    }
            }
    }
    // endregion userAction

    private var retrieveUsersJob: Job? = null
    private fun retrieveUsers() {
        val oldJob = retrieveUsersJob
        retrieveUsersJob = this@UserViewModelImpl
            .viewModelScope.launch {
                oldJob?.cancelAndJoin()

                userInteractor
                    .retrieveDBUsersFlow()
                    .map {
                        UserState.Success.LoadUsers(it)
                    }.catch<UserState> { e ->
                        emit(UserState.Failure(e))
                    }.collect {
                        launchInMain {
                            uiStateMutableStateFlow.emit(it)
                        }
                    }
            }
    }
}