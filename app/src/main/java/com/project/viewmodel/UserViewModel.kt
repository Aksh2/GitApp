package com.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.data.UserListState
import com.project.repository.GitRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val gitRepository: GitRepositoryImpl) : ViewModel() {
    private val _userListState = MutableStateFlow<UserListState>(UserListState.Loading)
    val userListState: MutableStateFlow<UserListState> = _userListState
    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _userListState.value = gitRepository.getUsers()
        }

    }
}