package com.example.capstoneproject.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.capstoneproject.data.local.ArchiveEntity
import com.example.capstoneproject.data.local.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM ArchiveEntity")
    fun getResult(): LiveData<List<ArchiveEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(parentEntity: UserEntity)

    @Query("SELECT * FROM UserEntity")
    fun getUser(): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertResult(user: ArchiveEntity)

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()
}