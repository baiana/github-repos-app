package com.ana.dev.githublistapp.di

import com.ana.dev.githublistapp.core.RetrofitConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = module {
    single<Retrofit> { RetrofitConfig.getInstance() }
    factory { RetrofitConfig.provideGithubAPI(get()) }
}