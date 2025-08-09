package com.dmendano.megaverse

import android.app.Application
import com.dmendano.data.di.dataModule
import com.dmendano.domain.di.domainModule
import com.dmendano.megaverse.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MegaverseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MegaverseApplication)
            modules(
                appModule,
                dataModule,
                domainModule
            )
        }
    }
}