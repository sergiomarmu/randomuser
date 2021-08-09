package com.sermarmu.domain.di

import com.sermarmu.domain.interactor.*
import org.koin.dsl.module

val domainModule = module {

    single<UserInteractor> { UserInteractorImpl(get(), get()) }

    single<NetworkInteractor> { NetworkInteractorImpl(get()) }

    single<LocalInteractor> { LocalInteractorImpl(get()) }
}

// region interactor
private fun provideUserInteractor(
    networkInteractor: UserInteractorImpl
): UserInteractor = networkInteractor


private fun provideNetworkInteractor(
    networkInteractor: NetworkInteractorImpl
): NetworkInteractor = networkInteractor

private fun provideLocalInteractor(
    localInteractor: LocalInteractorImpl
): LocalInteractor = localInteractor

// endregion interactor