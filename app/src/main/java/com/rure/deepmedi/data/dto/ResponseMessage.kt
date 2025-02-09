package com.rure.deepmedi.data.dto

import retrofit2.http.Field

data class ResponseMessage(
    val message: String,
    val email: String,
    @Field("pw")
    val password: String
)