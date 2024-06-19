package com.example.capstoneproject.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.capstoneproject.data.local.ArchiveEntity
import com.example.capstoneproject.data.local.database.HealthDao
import com.example.capstoneproject.data.local.database.HealthDatabase
import com.example.capstoneproject.data.local.SymptomsEntity
import com.example.capstoneproject.data.local.database.UserDao
import com.example.capstoneproject.data.local.database.UserDatabase
import com.example.capstoneproject.data.local.UserEntity
import com.example.capstoneproject.data.remote.ApiConfig
import com.example.capstoneproject.data.remote.ApiService
import com.example.capstoneproject.data.remote.response.Prediction
import com.example.capstoneproject.data.remote.response.SymptomsItem
import com.example.capstoneproject.ui.health.HealthFragmentSec
import com.example.capstoneproject.utils.Helper.Companion.dataStore
import com.example.capstoneproject.utils.Result
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class UserRepository (private val healthDao: HealthDao,
                      private val userDao: UserDao,
                      private val apiService: ApiService,
                      context: Context) {
    private val pref = Preference.getInstance(context.dataStore)

    private val _symptoms = MutableLiveData<List<SymptomsItem?>?>()
    private val symptoms: LiveData<List<SymptomsItem?>?> = _symptoms
    private val _prediction = MutableLiveData<Prediction?>()
    private val prediction: LiveData<Prediction?> = _prediction

    fun arrayListToJson(arrayList: ArrayList<String>): String {
        val gson = Gson()
        val dataWrapper = HealthFragmentSec.DataWrapper(arrayList)
        return gson.toJson(dataWrapper)
    }

    fun predict(data: ArrayList<String>): LiveData<Result<Prediction?>?> = liveData {
        emit(Result.Loading)
        try {
            val requestBody = arrayListToJson(data).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val response = apiService.predict(requestBody)
            val prediction = response.prediction
            _prediction.value = prediction
        } catch (e: Exception) {
            Log.d("StoryRepository", "${e.localizedMessage} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<Prediction?>?> = prediction.map { Result.Success(it) }
        emitSource(localData)
    }

    fun symptoms(): LiveData<Result<List<SymptomsItem?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSymptoms()
            val symptoms = response.symptoms
            _symptoms.value = symptoms
            val healthList = symptoms?.map { data ->
                SymptomsEntity(
                    0,
                    data?.symptom.toString(),
                    data?.translatedSymptom.toString(),
                    data?.imageUrl.toString(),
                    data?.description.toString(),
                    data?.category.toString(),
                )
            }
            healthDao.insertSymptoms(healthList)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<SymptomsItem?>?>> = symptoms.map { Result.Success(it) }
        emitSource(localData)
    }

    fun getSymptoms(category: String): LiveData<List<SymptomsEntity>> {
        return healthDao.symptoms(category)
    }

    fun getUser(): LiveData<UserEntity> {
        return userDao.getUser()
    }

    fun getResult(): LiveData<List<ArchiveEntity>> {
        return userDao.getResult()
    }

    fun getPrefUser() = pref.getUser().asLiveData()

    suspend fun delete() {
        try {
            healthDao.deleteSymptoms()
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
        }
    }

    suspend fun deleteUser() {
        try {
            userDao.deleteAll()
            pref.deleteUser()
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
        }
    }

    suspend fun insertUser(user: UserEntity) {
        try {
            userDao.insertUser(user)
            pref.saveUser(user)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
        }
    }

    suspend fun insertArchive(archive: ArchiveEntity) {
        try {
            userDao.insertResult(archive)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(context: Context): UserRepository {
            return instance ?: synchronized(this) {
                if(instance == null) {
                    val healthDao = HealthDatabase.getDatabase(context).healthDao()
                    val userDao = UserDatabase.getDatabase(context).userDao()
                    val apiService = ApiConfig.getApiService()
                    instance = UserRepository(healthDao, userDao, apiService, context)
                }
                return instance as UserRepository
            }
        }
    }

}