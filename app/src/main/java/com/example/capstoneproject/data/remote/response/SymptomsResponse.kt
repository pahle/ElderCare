package com.example.capstoneproject.data.remote.response

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SymptomsResponse(

	@field:SerializedName("symptoms")
	val symptoms: List<SymptomsItem?>? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Entity
data class SymptomsItem(
	@PrimaryKey
	@NonNull
	@field:SerializedName("symptom")
	val symptom: String,

	@field:SerializedName("translated_symptom")
	val translatedSymptom: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
