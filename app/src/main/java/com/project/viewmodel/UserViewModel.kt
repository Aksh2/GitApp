package com.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.data.BaseState
import com.project.data.GithubUser
import com.project.repository.GitRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val gitRepository: GitRepositoryImpl) : ViewModel() {
    private val _userListState = MutableStateFlow<BaseState<List<GithubUser>>>(BaseState.Loading())
    val userListState: MutableStateFlow<BaseState<List<GithubUser>>> = _userListState
    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _userListState.value = gitRepository.getUsers()
        }
    }
}