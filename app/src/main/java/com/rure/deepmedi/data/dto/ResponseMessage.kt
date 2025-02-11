package com.rure.deepmedi.data.dto

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class ResponseMessage(
    val message: String,
    val email: String,
    @SerializedName("pw")
    val password: String
)