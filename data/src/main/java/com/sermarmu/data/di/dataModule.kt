package com.sermarmu.data.di

import android.content.Context
import androidx.room.Room
import com.sermarmu.data.repository.LocalRepository
import com.sermarmu.data.repository.LocalRepositoryImpl
import com.sermarmu.data.repository.NetworkRepository
import com.sermarmu.data.repository.NetworkRepositoryImpl
import com.sermarmu.data.source.local.LocalApi
import com.sermarmu.data.source.local.LocalDatabase
import com.sermarmu.data.source.local.LocalSource
import com.sermarmu.data.source.local.LocalSourceImpl
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

    factory { provideLocalDatabase(get()) }

    factory { provideRoomDatabase(get()) }

    single<LocalSource> { LocalSourceImpl(get()) }

    single<LocalRepository> { LocalRepositoryImpl(get()) }
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

// region local
// region room
fun provideLocalDatabase(
    localDatabase: LocalDatabase
): LocalApi = localDatabase.localApi()

fun provideRoomDatabase(
    context: Context
) = Room.databaseBuilder(
    context,
    LocalDatabase::class.java,
    "breakingbad-database"
).build()
// endregion room

// region source
fun provideLocalSource(
    localSource: LocalSourceImpl
): LocalSource = localSource
// endregion source

// region repository
fun provideLocalRepository(
    localRepository: LocalRepositoryImpl
): LocalRepository = localRepository
// endregion repository
// endregion local
