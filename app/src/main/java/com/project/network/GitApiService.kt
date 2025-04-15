package com.project.network

import com.project.data.GitHubUserRepoDetails
import com.project.data.model.GitHubUserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApiService {
    @GET("users")
    suspend fun getListUsers(
        @Query("since") since: Int? = 1,
        @Query("per_page") perPage: Int? = 30
    ): Response<List<GitHubUserDetails>>

    @GET("users/{login}")
    suspend fun getUserDetails(@Path("login") login: String): Response<GitHubUserDetails>

    @GET("users/{login}/repos")
    suspend fun getReposList(@Path("login") login: String): Response<List<GitHubUserRepoDetails>>
}