package com.testask.kiosktabletapp.data.repositories

import android.util.Log
import com.testask.kiosktabletapp.data.models.ResponseAuth
import com.testask.kiosktabletapp.data.models.SecondResponse
import com.testask.kiosktabletapp.data.models.User1
import com.testask.kiosktabletapp.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) {
    suspend fun loginUser(login: String, pass: String): ResponseAuth? {
        return withContext(Dispatchers.IO) {
            try {
                val user = User1(login, pass)
                apiService.loginUser(user)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("UserRepository", "loginuser: ${e.toString()}," )
                null
            }
        }
    }

    suspend fun getData(token: String): SecondResponse? {
        return try {
            val response = apiService.fetchData("Bearer $token")
            if (response.isSuccessful) {
                Log.d("API Response", response.body().toString())
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}