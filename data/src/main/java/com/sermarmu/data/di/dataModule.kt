package com.sermarmu.data.di

import android.content.Context
import androidx.room.Room
import com.sermarmu.data.repository.LocalRepositoryImpl
import com.sermarmu.data.repository.NetworkRepositoryImpl
import com.sermarmu.data.source.local.LocalApi
import com.sermarmu.data.source.local.LocalDatabase
import com.sermarmu.data.source.local.LocalSource
import com.sermarmu.data.source.local.LocalSourceImpl
import com.sermarmu.data.source.network.NetworkApi
import com.sermarmu.data.source.network.NetworkSource
import com.sermarmu.data.source.network.NetworkSourceImpl
import com.sermarmu.domain.repository.LocalRepository
import com.sermarmu.domain.repository.NetworkRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val RETROFIT_API_BASE_URL = "retrofit_api_base_url"
const val ROOM_DATABASE_NAME = "room_database_name"


val dataModule = module {

    factory { provideOkkHttpClient() }

    factory { provideRetrofit(get(), get(named(RETROFIT_API_BASE_URL))) }

    factory { provideNetworkApi(get()) }

    single<NetworkSource> { NetworkSourceImpl(get()) }

    single<NetworkRepository> { NetworkRepositoryImpl(get()) }

    factory { provideLocalDatabase(get()) }

    factory { provideRoomDatabase(get(), get(named(ROOM_DATABASE_NAME))) }

    single<LocalSource> { LocalSourceImpl(get()) }

    single<LocalRepository> { LocalRepositoryImpl(get()) }
}


// region network
// region retrofit
private const val OKK_HTTP_CLIENT_TIMEOUT = 10L

private fun provideOkkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(
        HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
    ).connectTimeout(OKK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(OKK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(OKK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    baseUrl: String
): Retrofit = Retrofit.Builder()
    .addConverterFactory(
        GsonConverterFactory.create()
    )
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()

private fun provideNetworkApi(
    retrofit: Retrofit
): NetworkApi = retrofit.create(NetworkApi::class.java)
// endregion retrofit
// endregion network

// region local
// region room
fun provideLocalDatabase(
    localDatabase: LocalDatabase
): LocalApi = localDatabase.localApi()

fun provideRoomDatabase(
    context: Context,
    nameRoom: String
) = Room.databaseBuilder(
    context,
    LocalDatabase::class.java,
    nameRoom
).build()
// endregion room
// endregion local
