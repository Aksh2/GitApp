package com.project.repository

import com.project.data.BaseState
import com.project.data.GitHubUserDetails
import com.project.data.GithubUser
import com.project.data.UserListState
import retrofit2.Response

interface GitRepository {
    suspend fun getUsers(): BaseState<List<GithubUser>>
    suspend fun getUserDetails(loginId: String): BaseState<GitHubUserDetails>
    suspend fun <T> handleApiCall(
        apiCall: suspend () -> Response<T>,
        default: T? = null
    ): BaseState<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    BaseState.Success(body)
                } else if (default != null) {
                    BaseState.Success(default)
                } else {
                    BaseState.Error("Empty response body")
                }
            } else {
                BaseState.Error(response.message())
            }
        } catch (e: Exception) {
            BaseState.Error(e.message ?: "An unexpected error occurred")
        }
    }
}
