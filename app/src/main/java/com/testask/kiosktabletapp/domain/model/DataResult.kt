package com.testask.kiosktabletapp.domain.model

import com.testask.kiosktabletapp.data.models.SecondResponse

sealed class DataResult {
    data class  Success (val data: SecondResponse): DataResult()
    data class Error(val message: String) : DataResult()
    data object Loading : DataResult()
}