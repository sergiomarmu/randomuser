package com.sermarmu.randomuser.ui.feature.user

import androidx.lifecycle.viewModelScope
import com.sermarmu.core.base.BaseViewModel
import com.sermarmu.domain.interactor.UserInteractor
import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class UserViewModel : BaseViewModel() {

    // region state
    sealed class UserState {

        object Idle : UserState()

        data class Success(
            val users: List<UserModel>
        ) : UserState()

        data class Failure(
            val e: Throwable
        ) : UserState()

    }
    // endregion state

    // region shared flow
    abstract val uiStateSharedFlow: SharedFlow<UserState>
    // endregion shared flow

    // region user action
    /**
     * User request for retrieve more users
     */
    abstract fun onLoadMoreUsersAction()

    /**
     * User request for search users through a query
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

    private val uiStateMutableSharedFlow =
        MutableStateFlow<UserState>(UserState.Idle)
    override val uiStateSharedFlow: StateFlow<UserState> =
        uiStateMutableSharedFlow

    init {
        this@UserViewModelImpl
            .retrieveUsers()
    }

    // region userAction
    override fun onLoadMoreUsersAction() {
        launchUserAction {
            userInteractor
                .retrieveUsersFlow()
        }
    }

    override fun onQueryTypedAction(
        query: String
    ) {
        launchUserAction {
            userInteractor
                .retrieveUsersWithQueryFlow(
                    query = query
                )
        }
    }

    override fun onUserRemoveAction(
        userModel: UserModel
    ) {
        launchUserAction {
            userInteractor
                .deleteDBUser(
                    userModel = userModel
                )
        }
    }
    // endregion userAction

    private fun retrieveUsers() {
        launchUserAction {
            userInteractor
                .retrieveDBUsersFlow()
        }
    }

    private fun launchUserAction(
        action: suspend () -> Flow<List<UserModel>>
    ) {
        this@UserViewModelImpl
            .viewModelScope.launch {
                (try {
                    UserState.Success(action.invoke().first())
                } catch (e: Exception) {
                    UserState.Failure(e)
                }).let {
                    uiStateMutableSharedFlow.emit(it)
                }
            }
    }
}