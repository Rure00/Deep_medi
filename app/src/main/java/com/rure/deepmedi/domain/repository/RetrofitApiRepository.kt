package com.rure.deepmedi.domain.repository

import com.rure.deepmedi.data.entity.Entity
import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserAttribute
import com.rure.deepmedi.data.entity.UserData
import okhttp3.MultipartBody

interface RetrofitApiRepository {
    suspend fun uploadImageFile(file: MultipartBody.Part): Result<UserData>

    suspend fun userAuthentication(
        loginId: String,
        password: String
    ): Result<TokenList>

    suspend fun getUserInformation(
        token: String
    ): Result<Entity>

    suspend fun retrieveUserAttribute(
        userId: String
    ): Result<List<UserAttribute>>
}