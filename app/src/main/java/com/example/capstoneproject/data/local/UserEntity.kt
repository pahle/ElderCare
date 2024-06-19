package com.example.capstoneproject.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey
    val name: String,

    @field:ColumnInfo(name = "nickname")
    val nickname: String,

    @field:ColumnInfo(name = "gender")
    val gender: String,

    @field:ColumnInfo(name = "date")
    val date: String,

    @field:ColumnInfo(name = "number")
    val number: String,
)