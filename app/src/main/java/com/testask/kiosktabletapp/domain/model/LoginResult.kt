package com.testask.kiosktabletapp.domain.model

sealed class LoginResult {
    data object  Success : LoginResult()
    data class Error(val message: String) : LoginResult()
    data object Loading : LoginResult()
}
