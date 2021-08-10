package com.sermarmu.randomuser.di

import android.content.Context
import com.sermarmu.data.di.RETROFIT_API_BASE_URL
import com.sermarmu.data.di.ROOM_DATABASE_NAME
import com.sermarmu.randomuser.MessageLauncher
import com.sermarmu.randomuser.MessageLauncherImpl
import com.sermarmu.randomuser.R
import com.sermarmu.randomuser.ui.feature.user.UserViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<MessageLauncher> { MessageLauncherImpl(get()) }

    viewModel { UserViewModelImpl(get()) }

    single(named(RETROFIT_API_BASE_URL)) { provideRetrofitApBaseUrl(get()) }
    single(named(ROOM_DATABASE_NAME)) { provideRoomDatabaseName(get()) }

}

private fun provideRetrofitApBaseUrl(
    context: Context
) = context.getString(R.string.retrofit_api_base_url)


private fun provideRoomDatabaseName(
    context: Context
) = context.getString(R.string.room_database_name)