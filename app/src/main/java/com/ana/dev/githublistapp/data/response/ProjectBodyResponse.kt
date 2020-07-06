package com.ana.dev.githublistapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectBodyResponse(
    val result: List<ProjectResult>
)