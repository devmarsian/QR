package com.testask.kiosktabletapp.data.models

data class ResponseAuth(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val name: String,
    val token: String
)