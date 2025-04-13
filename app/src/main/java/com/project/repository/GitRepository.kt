package com.project.repository

import com.project.data.GithubUser
import com.project.data.UserListState
import retrofit2.Response

interface GitRepository {
    suspend fun getUsers(): UserListState
    suspend fun getUserDetails()
}