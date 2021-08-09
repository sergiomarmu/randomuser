package com.sermarmu.data.di

import com.sermarmu.data.repository.NetworkRepository
import com.sermarmu.data.repository.NetworkRepositoryImpl
import com.sermarmu.data.source.network.NetworkApi
import com.sermarmu.data.source.network.NetworkSource
import com.sermarmu.data.source.network.NetworkSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val dataModule = module {

    factory { provideOkkHttpClient() }

    factory { provideRetrofit(get()) }

    factory { provideNetworkApi(get()) }

    single<NetworkSource> { NetworkSourceImpl(get()) }

    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
}


// region network
// region retrofit
private fun provideOkkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(
        HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
    ).connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(10, TimeUnit.SECONDS)
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .addConverterFactory(
        GsonConverterFactory.create()
    )
    .baseUrl("https://randomuser.me/")
    .client(okHttpClient)
    .build()

private fun provideNetworkApi(
    retrofit: Retrofit
): NetworkApi = retrofit.create(NetworkApi::class.java)
// endregion retrofit

// region source
private fun provideNetworkSource(
    networkSource: NetworkSourceImpl
): NetworkSource = networkSource
// endregion source

// region repository
private fun provideNetworkRepository(
    networkRepository: NetworkRepositoryImpl
): NetworkRepository = networkRepository
// endregion repository
// endregion network
