package com.ana.dev.githublistapp.data.repository

import com.ana.dev.githublistapp.data.response.ProjectResult
import retrofit2.Response

interface ProjectsRepository {
    suspend fun getProjectsList(): Response<List<ProjectResult>>
    suspend fun searchProjectByName(name: String): Response<List<ProjectResult>>
}