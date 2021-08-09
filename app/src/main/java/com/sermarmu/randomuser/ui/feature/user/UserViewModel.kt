package com.sermarmu.randomuser.ui.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sermarmu.domain.interactor.UserInteractor
import com.sermarmu.domain.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {


    private val userMutableStateFlow = MutableStateFlow(emptyList<UserModel>())
    val userLiveData: StateFlow<List<UserModel>>
        get() = userMutableStateFlow


    init {
        viewModelScope.launch {
            userInteractor.retrieveUsersFlow()
                .collect {
                    userMutableStateFlow.value = it
                }
        }
    }

}