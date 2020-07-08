package com.ana.dev.githublistapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerResponse(
    val id: String,
    @Json(name = "login")
    val username: String,
    @Json(name = "avatar_url")
    val pictureLink: String,
    @Json(name = "url")
    val profileLink: String
)