package com.example.capstoneproject.utils

import com.example.capstoneproject.ui.user.AuthViewModel
import com.example.capstoneproject.data.UserRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.ui.archive.ArchiveViewModel
import com.example.capstoneproject.ui.health.HealthViewModel

class ViewModelFactory (private val userRepository: UserRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {3
        if (modelClass.isAssignableFrom(HealthViewModel::class.java)) {
            return HealthViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(ArchiveViewModel::class.java)) {
            return ArchiveViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(UserRepository.getInstance(context))
            }.also { instance = it }
    }
}