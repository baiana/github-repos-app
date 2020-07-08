package com.ana.dev.githublistapp.core

import android.app.Application
import com.ana.dev.githublistapp.di.apiModule
import com.ana.dev.githublistapp.di.displayInfoModule
import com.ana.dev.githublistapp.di.mainModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoreApplication)
            modules(apiModule, mainModule, displayInfoModule)
        }
    }
}