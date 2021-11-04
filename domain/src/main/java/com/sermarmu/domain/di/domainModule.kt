package com.sermarmu.domain.di

import com.sermarmu.domain.interactor.usercase.*
import org.koin.dsl.module

val domainModule = module {

    factory { GetUsers(get()) }
    factory { AddUsers(get()) }
    factory { SearchUsers(get()) }
    factory { DeleteUser(get()) }

    factory { UserCases(get(), get(), get(), get()) }
}

// region user case
private fun provideUserCase(
   userCases: UserCases
) = userCases
// endregion user case