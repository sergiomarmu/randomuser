package com.sermarmu.randomuser.di

import com.sermarmu.randomuser.MessageLauncher
import com.sermarmu.randomuser.MessageLauncherImpl
import com.sermarmu.randomuser.ui.feature.user.UserViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<MessageLauncher> { MessageLauncherImpl(get()) }

    viewModel { UserViewModelImpl(get()) }

}


private fun provideMessageLauncher(
    messageLauncher: MessageLauncherImpl
): MessageLauncher = messageLauncher