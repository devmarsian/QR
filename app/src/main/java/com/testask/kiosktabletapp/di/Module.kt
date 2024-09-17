package com.testask.kiosktabletapp.di

import com.testask.kiosktabletapp.data.KioskHandler
import com.testask.kiosktabletapp.data.network.ApiService
import com.testask.kiosktabletapp.data.repositories.UserRepository
import com.testask.kiosktabletapp.domain.usecase.EncryptDataUseCase
import com.testask.kiosktabletapp.domain.usecase.FetchUserDataUseCase
import com.testask.kiosktabletapp.domain.usecase.GenerateQRCodeUseCase
import com.testask.kiosktabletapp.domain.usecase.KioskModeUseCase
import com.testask.kiosktabletapp.domain.usecase.LoginUserUseCase
import com.testask.kiosktabletapp.presentation.viewmodels.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { KioskHandler }
    single { LoginUserUseCase(get()) }
    single { FetchUserDataUseCase(get()) }
    single { GenerateQRCodeUseCase() }
    single { EncryptDataUseCase() }
    single { KioskModeUseCase(get()) }
    single { UserRepository(get()) }

    viewModel { UserViewModel(
        loginUserUseCase = get(),
        fetchUserDataUseCase = get(),
        generateQRCodeUseCase = get(),
        encryptDataUseCase = get(),
        kioskModeUseCase = get()
    ) }
}

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("http://90.156.204.236/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}
