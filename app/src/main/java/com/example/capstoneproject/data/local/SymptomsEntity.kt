package com.example.capstoneproject.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SymptomsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @field:ColumnInfo(name = "symptom")
    val symptom: String,

    @field:ColumnInfo(name = "translated_symptom")
    val translatedSymptom: String,

    @field:ColumnInfo(name = "image_url")
    val imageUrl: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "category")
    val category: String,
)
