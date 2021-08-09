package com.sermarmu.randomuser.di

import com.sermarmu.randomuser.ui.feature.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { UserViewModel(get()) }

}