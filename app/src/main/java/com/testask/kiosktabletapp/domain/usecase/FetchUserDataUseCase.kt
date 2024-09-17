package com.testask.kiosktabletapp.domain.usecase

import com.testask.kiosktabletapp.data.models.SecondResponse
import com.testask.kiosktabletapp.data.repositories.UserRepository

class FetchUserDataUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(token: String): SecondResponse? {
        return userRepository.getData(token)
    }
}
