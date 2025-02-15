package com.rure.deepmedi.data.source

import android.util.Log
import com.rure.deepmedi.data.dto.LoginRequestDto
import com.rure.deepmedi.data.entity.Entity
import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserAttribute
import com.rure.deepmedi.data.entity.UserData
import com.rure.deepmedi.data.retrofit.ApiService
import com.rure.deepmedi.data.retrofit.UploadService
import okhttp3.MultipartBody
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val apiService: ApiService,
    private val uploadService: UploadService
) {
    private val tag = "ApiDataSource"

    suspend fun uploadImageFile(file: MultipartBody.Part): Result<UserData> {
        return kotlin.runCatching {
            val response = uploadService.uploadImageFile(file)

            if(response.isSuccessful && response.body()?.code == 200) {
                val body = response.body()!!
                UserData(
                    email = body.message.email,
                    password = body.message.password
                )
            } else {
                throw Exception("code:${response.code()}) ${response.message()}")
            }
        }.onFailure {
            Log.e(tag, "fail to uploadImageFile) ${it.message}")
            it.printStackTrace()
            throw it
        }
    }

    suspend fun userAuthentication(
        loginId: String,
        password: String
    ): Result<TokenList> {
        return kotlin.runCatching {
            val response = apiService.userAuthentication(
                LoginRequestDto(loginId, password)
            )

            if(response.isSuccessful && response.code() == 200) {
                val body = response.body()!!
                TokenList(
                    token = body.token,
                    refreshToken = body.refreshToken,
                    scope = body.scope
                )
            } else {
                throw Exception("code:${response.code()}) ${response.message()}")
            }
        }.onFailure {
            Log.e(tag, "fail to userAuthentication) ${it.message}")
            it.printStackTrace()
            throw it
        }
    }


    suspend fun getUserInformation(
        token: String
    ): Result<Entity> {
        return kotlin.runCatching {
            val response = apiService.getUserInformation(
                authentication = "Bearer $token"
            )

            if(response.isSuccessful && response.code() == 200) {
                val body = response.body()!!
                body.entity
            } else {
                throw Exception("code:${response.code()}) ${response.message()}")
            }
        }.onFailure {
            Log.e(tag, "fail to getUserInformation) ${it.message}")
            it.printStackTrace()
            throw it
        }
    }

    suspend fun retrieveUserAttribute(
        token: String,
        userId: String
    ): Result<List<UserAttribute>> {
        return kotlin.runCatching {
            Log.d("HomeScreen", "userId: $userId")
            val response = apiService.retrieveUserAttributes("Bearer $token", userId)

            if(response.isSuccessful && response.code() == 200) {
                response.body() ?: listOf()
            } else {
                throw Exception("code:${response.code()}) ${response.message()}")
            }
        }.onFailure {
            Log.e(tag, "fail to retrieveUserAttribute) ${it.message}")
            it.printStackTrace()
            throw it
        }
    }


}