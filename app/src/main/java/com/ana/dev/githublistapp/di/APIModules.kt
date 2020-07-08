package com.ana.dev.githublistapp.di

import com.ana.dev.githublistapp.retrofit.RetrofitConfig
import com.ana.dev.githublistapp.retrofit.GithubServices
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<Retrofit> { RetrofitConfig.getInstance() }
    factory<GithubServices> { RetrofitConfig.provideGithubAPI(get()) }
}