package com.ana.dev.githublistapp.core

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig {
    const val BASE_URL = "https://api.github.com"
    fun getInstance() =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    fun provideGithubAPI(retrofit: Retrofit) = retrofit.create(GithubServices::class.java)
}
