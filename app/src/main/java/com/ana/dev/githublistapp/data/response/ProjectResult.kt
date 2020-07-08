package com.ana.dev.githublistapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectResult(
    val id: String,
    val name: String,
    @Json(name = "owner")
    val user: OwnerResponse,
    val description: String? = "",
    @Json(name= "html_url")
    val url: String
)