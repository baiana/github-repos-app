package com.ana.dev.githublistapp.data.model


data class Project(
    val id: String,
    val name: String,
    val user: User,
    val description: String,
    val url: String
)
