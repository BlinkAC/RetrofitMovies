package com.example.recyclerview.utils

import com.google.gson.annotations.SerializedName

data class RateResponse(
    val status_code: Int,
    val status_message: String
)

data class RateInfo(
    @SerializedName("value") val rating: Double
)