package com.testask.kiosktabletapp.data.models

data class SecondResponse(
    val `data`: DataContainer,
    val message: String,
    val success: Boolean
)

data class DataContainer(
    val user: User,
    val is_blocked: Boolean,
    val qr: Qr,
    val request: List<Any>
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val is_confirm: Int,
    val email_verified_at: String?,
    val created_at: String,
    val updated_at: String,
    val thema: Int,
    val lang: String
)

data class Qr(
    val cipher: String,
    val key: String,
    val payload: Payload
)

data class Payload(
    val block_id: Int,
    val block_name: String,
    val block_ping: Int,
    val stage_name: String,
    val stage_address: String
)