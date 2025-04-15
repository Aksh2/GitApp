package com.project.data.model

import com.squareup.moshi.Json

data class GitHubUserDetails(
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    val login: String?,
    val name: String?,
    val followers: Int?,
    val following: Int?,
    @Json(name = "repos_url")
    val reposUrl: String?,
)
