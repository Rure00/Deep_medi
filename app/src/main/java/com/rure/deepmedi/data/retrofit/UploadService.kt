package com.rure.deepmedi.data.retrofit

import com.rure.deepmedi.data.dto.UploadImageResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {
    @POST("deepmedi-test-first")
    suspend fun uploadImageFile(
        @Part file: MultipartBody.Part
    ): Response<UploadImageResponseDto>
}