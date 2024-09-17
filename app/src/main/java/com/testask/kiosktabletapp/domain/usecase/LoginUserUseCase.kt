package com.testask.kiosktabletapp.domain.usecase

import com.testask.kiosktabletapp.data.models.ResponseAuth
import com.testask.kiosktabletapp.data.models.User1
import com.testask.kiosktabletapp.data.repositories.UserRepository

class LoginUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(login: String, password: String): ResponseAuth? {
        return userRepository.loginUser(login, password)
    }
}
