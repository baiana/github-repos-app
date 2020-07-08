package com.ana.dev.githublistapp.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "total_count")
    val amount: Int,
    @Json(name = "items")
    val result: List<ProjectResult>?
)