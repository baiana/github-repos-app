package com.ana.dev.githublistapp.data.repository

import com.ana.dev.githublistapp.data.GithubServices
import com.ana.dev.githublistapp.data.response.ProjectBodyResponse
import org.koin.java.KoinJavaComponent.inject

class ProjectsRepositoryImp() : ProjectsRepository {

    private val API: GithubServices by inject(GithubServices::class.java)

    override fun getProjectsList(): ProjectBodyResponse {
       API.listProjects()
        //todo
    }

}