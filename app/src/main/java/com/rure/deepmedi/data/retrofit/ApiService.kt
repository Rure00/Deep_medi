package com.rure.deepmedi.data.retrofit

import com.rure.deepmedi.data.dto.GetUserInfoResponseDto
import com.rure.deepmedi.data.dto.LoginRequestDto
import com.rure.deepmedi.data.dto.LoginResponseDto
import com.rure.deepmedi.data.dto.RetrieveUserAttrResponseDto
import com.rure.deepmedi.data.entity.UserAttribute
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun userAuthentication(
        @Body loginRequestDto: LoginRequestDto
    ): Response<LoginResponseDto>

    @GET("auth/user")
    suspend fun getUserInformation(
        @Header("Authorization") authentication: String
    ): Response<GetUserInfoResponseDto>

    @GET("plugins/telemetry/USER/{userId}/values/attributes/SERVER_SCOPE")
    suspend fun retrieveUserAttributes(
        @Header("Authorization") authentication: String,
        @Path("userId") userId: String
    ): Response<List<UserAttribute>>
}