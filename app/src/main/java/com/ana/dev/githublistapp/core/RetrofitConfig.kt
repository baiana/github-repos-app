package com.ana.dev.githublistapp.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException


object RetrofitConfig {
    const val BASE_URL = "https://api.github.com"
    fun getInstance() =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).
            client(generateClient().build())
            .build()

    fun provideGithubAPI(retrofit: Retrofit) = retrofit.create(GithubServices::class.java)

    private fun generateClient(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader("authorization", "Token d9ac84e3a6d63072c2f6334914d5e53f40450b72").build()
            chain.proceed(request)
        }
        return httpClient
    }


}

