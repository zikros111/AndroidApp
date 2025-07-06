package com.taskmaster.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmaster.data.entity.User
import com.taskmaster.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val currentUser = userRepository.getCurrentUser()
    
    private val _searchResults = MutableLiveData<List<User>>()
    val searchResults: LiveData<List<User>> = _searchResults

    private val _selectedUser = MutableLiveData<User?>()
    val selectedUser: LiveData<User?> = _selectedUser

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _searchResults.value = userRepository.searchUsers(query)
        }
    }

    fun selectUser(user: User) {
        _selectedUser.value = user
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    fun getUserById(userId: Long) {
        viewModelScope.launch {
            _selectedUser.value = userRepository.getUserById(userId)
        }
    }
}
