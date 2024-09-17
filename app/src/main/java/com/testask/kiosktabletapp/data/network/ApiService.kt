package com.testask.kiosktabletapp.data.network

import com.testask.kiosktabletapp.data.models.ResponseAuth
import com.testask.kiosktabletapp.data.models.SecondResponse
import com.testask.kiosktabletapp.data.models.User1
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun loginUser(@Body user: User1): ResponseAuth
    @GET("test")
    suspend fun fetchData(@Header("Authorization") token: String): Response<SecondResponse>
}