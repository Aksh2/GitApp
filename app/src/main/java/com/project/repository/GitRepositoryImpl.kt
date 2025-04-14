package com.project.repository

import com.project.data.BaseState
import com.project.data.GitHubUserDetails
import com.project.data.GithubUser
import com.project.network.GitApiService

class GitRepositoryImpl(private val gitApiService: GitApiService) : GitRepository {
    override suspend fun getUsers(): BaseState<List<GithubUser>> {
        return handleApiCall(
            apiCall = { gitApiService.getListUsers() },
            default = emptyList()
        )
    }

    override suspend fun getUserDetails(loginId: String): BaseState<GitHubUserDetails> {
        return handleApiCall(
            apiCall = { gitApiService.getUserDetails(loginId) },
            default = GitHubUserDetails(
                null, null, null, null, null
            )
        )
    }


}