package com.rure.deepmedi.data.dto

data class LoginResponseDto(
    val token: String,
    val refreshToken: String,
    val scope: String?,
)
