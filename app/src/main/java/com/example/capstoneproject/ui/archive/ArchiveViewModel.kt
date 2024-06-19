package com.example.capstoneproject.ui.archive

import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.UserRepository

class ArchiveViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getArchive() = userRepository.getResult()
}