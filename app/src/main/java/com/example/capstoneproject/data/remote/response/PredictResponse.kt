package com.example.capstoneproject.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("probability")
	val probability: String? = null,

	@field:SerializedName("prediction")
	val prediction: Prediction? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Prediction(

	@field:SerializedName("treatment")
	val treatment: String? = null,

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("translated_disease")
	val translatedDisease: String? = null
)
