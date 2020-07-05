package com.ana.dev.githublistapp.data.response

data class ProjectResponse(
    val id: String,
    val name: String,
    val owner: OwnerResponse,
    val description: String,
    val url: String
)