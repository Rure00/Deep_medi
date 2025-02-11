package com.rure.deepmedi.data.dto

import com.google.gson.annotations.SerializedName
import com.rure.deepmedi.data.entity.Entity

data class GetUserInfoResponseDto(
    @SerializedName("id")
    val entity: Entity
)
