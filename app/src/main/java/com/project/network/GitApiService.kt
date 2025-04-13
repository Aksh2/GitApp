package com.project.network

import com.project.data.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitApiService {
    @GET("users")
    suspend fun getListUsers(
        @Query("since") since: Int? = 1,
        @Query("per_page") perPage: Int? = 30
    ): Response<List<GithubUser>>
}