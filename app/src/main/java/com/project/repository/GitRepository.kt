package com.project.repository

import com.project.data.BaseState
import com.project.data.Constants.SOMETHING_WENT_WRONG
import com.project.data.GitHubUserRepoDetails
import com.project.data.model.GitHubUserDetails
import retrofit2.Response

interface GitRepository {
    suspend fun getUsers(): BaseState<List<GitHubUserDetails>>
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
                    BaseState.Error(SOMETHING_WENT_WRONG)
                }
            } else {
                BaseState.Error(response.message())
            }
        } catch (e: Exception) {
            BaseState.Error(e.message ?: SOMETHING_WENT_WRONG)
        }
    }

    suspend fun getRepositoryList(loginId: String): BaseState<List<GitHubUserRepoDetails>>
}
