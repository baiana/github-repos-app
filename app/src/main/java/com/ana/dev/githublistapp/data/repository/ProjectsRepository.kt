package com.ana.dev.githublistapp.data.repository

import com.ana.dev.githublistapp.data.response.ProjectResult
import com.ana.dev.githublistapp.data.response.SearchResult
import retrofit2.Response

interface ProjectsRepository {
    suspend fun getProjectsList(): Response<List<ProjectResult>>
    suspend fun searchProjectByName(name: String): Response<SearchResult>
}