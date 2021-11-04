package com.sermarmu.data.utils

import com.sermarmu.data.source.network.userrandom.UserRandomApi
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun MockWebServer.retrieveApi(
    fakeUrl: String
): UserRandomApi = Retrofit
    .Builder()
    .baseUrl(this.url(fakeUrl))
    .addConverterFactory(
        GsonConverterFactory
            .create()
    ).build()
    .create(UserRandomApi::class.java)