package com.sermarmu.randomuser

import android.app.Application
import com.sermarmu.data.di.dataModule
import com.sermarmu.domain.di.domainModule
import com.sermarmu.randomuser.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RandomUserApplication)
            modules(appModule, domainModule, dataModule)
        }
    }

}