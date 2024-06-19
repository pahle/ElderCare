package com.example.capstoneproject.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.local.UserEntity
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun setUser(user : UserEntity) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun getPrefUser() = userRepository.getPrefUser()
    fun delete() {
        viewModelScope.launch {
            userRepository.deleteUser()
        }
    }
}