package com.project.network

import com.project.data.GitHubUserDetails
import com.project.data.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApiService {
    @GET("users")
    suspend fun getListUsers(
        @Query("since") since: Int? = 1,
        @Query("per_page") perPage: Int? = 30
    ): Response<List<GithubUser>>

    @GET("users/{login}")
    suspend fun getUserDetails(@Path("login") login: String): Response<GitHubUserDetails>
}