package com.project.data

import com.squareup.moshi.Json

/**
 * Data class which holds the repository details of the particular user
 */
data class GitHubUserRepoDetails(
    val name: String?,
    val description: String?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    val fork: Boolean?,
    @Json(name = "stargazers_count")
    val starsCount: Int?,
    val language: String?,
)