package com.project.data

sealed class UserListState {
    data object Loading : UserListState()
    data class Success(val users: List<GithubUser>) : UserListState()
    data class Error(val message: String) : UserListState()

}