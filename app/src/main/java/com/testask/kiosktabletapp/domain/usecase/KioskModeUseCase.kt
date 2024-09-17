package com.testask.kiosktabletapp.domain.usecase

import android.app.Activity
import android.content.Context
import com.testask.kiosktabletapp.data.KioskHandler

class KioskModeUseCase(
    private val kioskHandler: KioskHandler
) {

    fun initializeKioskMode(context: Context): Boolean {
        return kioskHandler.initialize(context)
    }

    fun startKioskMode(context: Context) {
        kioskHandler.startKioskMode(context)
    }

    fun stopKioskMode(context: Activity) {
        kioskHandler.stopKiosk(context)
    }
}
