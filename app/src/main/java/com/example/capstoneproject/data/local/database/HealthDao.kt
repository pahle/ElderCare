package com.example.capstoneproject.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.capstoneproject.data.local.SymptomsEntity

@Dao
interface HealthDao {
    @Query("SELECT * FROM SymptomsEntity where category = :category")
    fun symptoms(category: String): LiveData<List<SymptomsEntity>>

    @Query("DELETE FROM SymptomsEntity")
    suspend fun deleteSymptoms()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSymptoms(parentEntity: List<SymptomsEntity?>?)
}