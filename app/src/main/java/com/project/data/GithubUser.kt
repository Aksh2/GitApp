package com.project.data

import kotlinx.serialization.Serializable

@Serializable
data class GithubUser (
    val login: String,
    val id: Int,
    val avatar_url: String,
)