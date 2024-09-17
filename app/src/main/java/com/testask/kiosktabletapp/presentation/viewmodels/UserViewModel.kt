package com.testask.kiosktabletapp.presentation.viewmodels

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testask.kiosktabletapp.data.models.Payload
import com.testask.kiosktabletapp.data.models.ResponseAuth
import com.testask.kiosktabletapp.data.models.SecondResponse
import com.testask.kiosktabletapp.domain.model.DataResult
import com.testask.kiosktabletapp.domain.usecase.EncryptDataUseCase
import com.testask.kiosktabletapp.domain.usecase.FetchUserDataUseCase
import com.testask.kiosktabletapp.domain.usecase.GenerateQRCodeUseCase
import com.testask.kiosktabletapp.domain.usecase.KioskModeUseCase
import com.testask.kiosktabletapp.domain.model.LoginResult
import com.testask.kiosktabletapp.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class UserViewModel (
    private val loginUserUseCase: LoginUserUseCase,
    private val fetchUserDataUseCase: FetchUserDataUseCase,
    private val generateQRCodeUseCase: GenerateQRCodeUseCase,
    private val encryptDataUseCase: EncryptDataUseCase,
    private val kioskModeUseCase: KioskModeUseCase,
) : ViewModel(), KoinComponent {

    private val _loginState = MutableLiveData<LoginResult>()
    val loginState: LiveData<LoginResult> = _loginState

    private val _userDataState = MutableLiveData<DataResult>()
    val userDataState: LiveData<DataResult> = _userDataState

    private val _qrBitmap = MutableLiveData<Bitmap?>(null)
    val qrBitmap: LiveData<Bitmap?> = _qrBitmap

    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            _loginState.postValue(LoginResult.Loading)
            try {
                val response = loginUserUseCase(login, password)
                handleLoginResponse(response)
            } catch (e: Exception) {
                _loginState.postValue(LoginResult.Error("Ошибка авторизации: ${e.localizedMessage}"))
            }
        }
    }

    private fun handleLoginResponse(response: ResponseAuth?) {
        when {
            response == null -> {
                _loginState.postValue(LoginResult.Error("Неизвестная ошибка"))
            }
            response.success -> {
                _loginState.postValue(LoginResult.Success)
                val token = response.data.token
                fetchUserData(token)
            }
            else -> {
                _loginState.postValue(LoginResult.Error("Ошибка авторизации: ${response.message}"))
            }
        }
    }

    private fun fetchUserData(token: String) {
        viewModelScope.launch {
            val userData = fetchUserDataUseCase(token)
            handleUserData(userData)
        }
    }

    private fun handleUserData(userData: SecondResponse?) {
        when{
            userData == null -> {
                _userDataState.postValue(DataResult.Error("Неизвестная ошибка"))
            }
            userData.success -> {
                _userDataState.postValue(DataResult.Success(userData))
            }
            else -> _userDataState.postValue(DataResult.Error("Ошибка подгрузки: ${userData.message}"))
        }
    }

    fun handleKioskMode(context: Context, isBlocked: Boolean) {
        val activity = (context as? Activity) ?: return
        if (isBlocked) {
                kioskModeUseCase.initializeKioskMode(context)
                kioskModeUseCase.startKioskMode(context)
            } else {
                kioskModeUseCase.stopKioskMode(activity)
            }
    }

    fun clearNavigationState() {
        _loginState.postValue(LoginResult.Error("Вы вышли из системы"))
    }

    fun updateQRCode(key: String, payload: Payload) {
        viewModelScope.launch {
            val encryptedPayload = encryptDataUseCase(key, payload.toString())
            val qrCodeBitmap = generateQRCodeUseCase(encryptedPayload, 512, 512)
            _qrBitmap.postValue(qrCodeBitmap)
        }
    }
}