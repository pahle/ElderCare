package com.example.capstoneproject.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.capstoneproject.data.local.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Preference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserEntity> {
        return dataStore.data.map { preferences ->
            UserEntity(
                preferences[NAME_KEY] ?: "",
                preferences[NICKNAME_KEY] ?: "",
                preferences[GENDER_KEY] ?: "",
                preferences[DATE_KEY] ?: "",
                preferences[NUMBER_KEY] ?: ""
            )
        }
    }

    suspend fun saveUser(user: UserEntity) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[NICKNAME_KEY] = user.nickname
            preferences[GENDER_KEY] = user.gender
            preferences[DATE_KEY] = user.date
            preferences[NUMBER_KEY] = user.number
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[NICKNAME_KEY] = ""
            preferences[GENDER_KEY] = ""
            preferences[DATE_KEY] = ""
            preferences[NUMBER_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Preference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val NICKNAME_KEY = stringPreferencesKey("email")
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val DATE_KEY = stringPreferencesKey("date")
        private val NUMBER_KEY = stringPreferencesKey("number")

        fun getInstance(dataStore: DataStore<Preferences>): Preference {
            return INSTANCE ?: synchronized(this) {
                val instance = Preference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}