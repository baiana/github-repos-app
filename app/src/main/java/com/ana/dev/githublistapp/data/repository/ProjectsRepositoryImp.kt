package com.ana.dev.githublistapp.data.repository

import com.ana.dev.githublistapp.core.GithubServices
import com.ana.dev.githublistapp.data.response.ProjectResult
import com.ana.dev.githublistapp.data.response.SearchResult
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Response

class ProjectsRepositoryImp() : ProjectsRepository {

    private val API: GithubServices by inject(
        GithubServices::class.java)

    override suspend fun getProjectsList(): Response<List<ProjectResult>> = API.listProjects()
    override suspend fun searchProjectByName(name:String): Response<SearchResult> = API.searchProjects(name)


}