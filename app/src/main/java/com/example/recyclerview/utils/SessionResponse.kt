package com.example.recyclerview.utils

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    val success: String,
    val session_id: String
)

data class TokenInfo(
    @SerializedName("request_token") val myRequestToken: String
)
