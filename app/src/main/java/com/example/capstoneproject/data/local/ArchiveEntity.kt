package com.example.capstoneproject.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["name"],
            childColumns = ["userName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ArchiveEntity(
    @field:ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @field:ColumnInfo(name = "userName")
    val userName: String,

    @field:ColumnInfo(name = "diagnose")
    val diagnose: String,

    @field:ColumnInfo(name = "date")
    val date: String,

    @field:ColumnInfo(name = "detail")
    val detail: String,
)