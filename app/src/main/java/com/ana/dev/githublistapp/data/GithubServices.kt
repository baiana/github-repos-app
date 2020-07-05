package com.ana.dev.githublistapp.data

import android.telecom.Call
import com.ana.dev.githublistapp.data.response.ProjectResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubServices {
    @GET("/repositories")
    fun listProjects(): Call<List<ProjectResponse>>

    @GET("/search/repositories?q={search}")
    fun searchProjects(@Path("search") query: String): Call<List<ProjectResponse>>

}