package com.ana.dev.githublistapp.data

import com.ana.dev.githublistapp.data.response.ProjectResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubServices {
    @GET("/repositories")
    fun listProjects(): Call<List<ProjectResult>>

    @GET("/search/repositories")
    fun searchProjects(@Query("q") query: String): Call<List<ProjectResult>>

}