package com.project.repository

import com.project.data.UserListState
import com.project.network.GitApiService

class GitRepositoryImpl(private val gitApiService: GitApiService) : GitRepository {
    override suspend fun getUsers(): UserListState {
        val response = gitApiService.getListUsers()
        return if (response.isSuccessful) {
            UserListState.Success(response.body().orEmpty())
            // Handle the list of users
        } else {
            UserListState.Error(response.message())
        }
    }

    override suspend fun getUserDetails() {
        TODO("Not yet implemented")
    }
}