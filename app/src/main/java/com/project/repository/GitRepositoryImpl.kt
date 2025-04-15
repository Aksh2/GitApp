package com.project.repository

import com.project.data.BaseState
import com.project.data.GitHubUserRepoDetails
import com.project.data.model.GitHubUserDetails
import com.project.network.GitApiService

class GitRepositoryImpl(private val gitApiService: GitApiService) : GitRepository {
    override suspend fun getUsers(): BaseState<List<GitHubUserDetails>> {
        return handleApiCall(
            apiCall = { gitApiService.getListUsers() },
            default = emptyList()
        )
    }

    override suspend fun getUserDetails(loginId: String): BaseState<GitHubUserDetails> {
        return handleApiCall(
            apiCall = { gitApiService.getUserDetails(loginId) },
            default = null
        )
    }

    override suspend fun getRepositoryList(loginId: String): BaseState<List<GitHubUserRepoDetails>> {
        return handleApiCall(
            apiCall = { gitApiService.getReposList(loginId) },
            default = emptyList()
        )
    }

}