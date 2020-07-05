package com.ana.dev.githublistapp.data

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubServices{
    @GET("users/{user}/repos")
//    fun listRepos(): Call<List<Repo>>

}