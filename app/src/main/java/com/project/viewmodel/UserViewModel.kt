package com.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.data.BaseState
import com.project.data.GitHubUserRepoDetails
import com.project.data.model.GitHubUserDetails
import com.project.repository.GitRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val gitRepository: GitRepositoryImpl) : ViewModel() {
    private val _userListState =
        MutableStateFlow<BaseState<List<GitHubUserDetails>>>(BaseState.Loading())
    private val _userDetailsState =
        MutableStateFlow<BaseState<GitHubUserDetails>>(BaseState.Loading())

    private val _userRepositoryListState =
        MutableStateFlow<BaseState<List<GitHubUserRepoDetails>>>(BaseState.Loading())

    val userListState: StateFlow<BaseState<List<GitHubUserDetails>>> = _userListState
    val userDetails: StateFlow<BaseState<GitHubUserDetails>> = _userDetailsState
    val userRepositoryListState: StateFlow<BaseState<List<GitHubUserRepoDetails>>> =
        _userRepositoryListState

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _userListState.value = gitRepository.getUsers()
        }
    }

    fun getUserDetails(loginId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _userDetailsState.value = gitRepository.getUserDetails(loginId)
        }
    }

    fun getUserRepositoryList(loginId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _userRepositoryListState.value = gitRepository.getRepositoryList(loginId)
        }

    }
}