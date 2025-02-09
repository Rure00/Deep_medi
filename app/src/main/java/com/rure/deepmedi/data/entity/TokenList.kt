package com.rure.deepmedi.data.entity

data class TokenList(
    val token: String,
    val refreshToken: String,
    val scope: String?
)
