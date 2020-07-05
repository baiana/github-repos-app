package com.ana.dev.githublistapp.data.repository

import com.ana.dev.githublistapp.data.response.ProjectBodyResponse

interface ProjectsRepository {
    fun getProjectsList(): ProjectBodyResponse
}