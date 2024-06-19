package com.example.capstoneproject.data.remote

import com.example.capstoneproject.data.remote.response.PredictResponse
import com.example.capstoneproject.data.remote.response.SymptomsResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("predict")
    suspend fun predict(@Body symptoms: RequestBody): PredictResponse

    @GET("symptoms")
    suspend fun getSymptoms(): SymptomsResponse
}