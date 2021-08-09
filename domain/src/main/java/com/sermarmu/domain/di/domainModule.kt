package com.sermarmu.domain.di

import com.sermarmu.domain.interactor.*
import org.koin.dsl.module

val domainModule = module {

    single<UserInteractor> { UserInteractorImpl(get()) }

    single<NetworkInteractor> { NetworkInteractorImpl(get()) }
}

// region interactor
private fun provideUserInteractor(
    networkInteractor: UserInteractorImpl
): UserInteractor = networkInteractor


private fun provideNetworkInteractor(
    networkInteractor: NetworkInteractorImpl
): NetworkInteractor = networkInteractor