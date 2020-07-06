package com.ana.dev.githublistapp.core

import com.ana.dev.githublistapp.data.response.ProjectResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubServices {
    @GET("/repositories")
    suspend fun listProjects(): Response<List<ProjectResult>>

    @GET("/search/repositories")
    suspend fun searchProjects(@Query("q") query: String): Response<List<ProjectResult>>

}