package com.example.capstoneproject.ui.health

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.local.ArchiveEntity
import kotlinx.coroutines.launch

class HealthViewModel(private val userRepository: UserRepository) : ViewModel() {

    val symptomsDesc = MutableLiveData<List<String>>()
    var healthList: MutableLiveData<MutableList<String>> = MutableLiveData()
    private var list = mutableListOf<String>()

    fun setData(data: String) {
        list.add(data)
        healthList.value = list
    }

    fun getSymptomsDesc(data: List<String>) {
        symptomsDesc.value = data
    }

    fun deleteAll() {
        viewModelScope.launch {
            userRepository.delete()
        }
    }

    fun setArchive(archive: ArchiveEntity) {
        viewModelScope.launch {
            userRepository.insertArchive(archive)
        }
    }

    fun getUser() = userRepository.getUser()
    fun searchSymptoms(category : String)= userRepository.getSymptoms(category)
    fun predict(data : ArrayList<String>) = userRepository.predict(data)
    fun symptoms() = userRepository.symptoms()
}