package com.ana.dev.githublistapp.retrofit

import com.ana.dev.githublistapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitConfig {

    fun getInstance() =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                generateClient()
                    .build()
            )
            .build()

    fun provideGithubAPI(retrofit: Retrofit) = retrofit.create(GithubServices::class.java)

    private fun generateClient(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("authorization", "token ${BuildConfig.API_TOKEN}")
                    .build()
            chain.proceed(request)
        }
        return httpClient
    }


}

