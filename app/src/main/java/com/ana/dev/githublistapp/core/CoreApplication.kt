package com.ana.dev.githublistapp.core

import android.app.Application
import com.ana.dev.githublistapp.data.GithubServices
import com.ana.dev.githublistapp.di.apiModule
import com.ana.dev.githublistapp.di.mainModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoreApplication:Application() {

    private val API: GithubServices by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoreApplication)
            modules(apiModule, mainModule)
        }
    }


}