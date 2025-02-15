package com.rure.deepmedi.domain.usercase

import android.util.Log
import com.rure.deepmedi.domain.repository.RetrofitApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SendImageUseCase @Inject constructor(
    private val retrofitApiRepository: RetrofitApiRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val tag = "SendImageUseCase"
    suspend operator fun invoke(file: File) = kotlin.runCatching {
        val fileBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file", file.name, fileBody)

        withContext(ioDispatcher) {
            retrofitApiRepository.uploadImageFile(part).getOrThrow()
        }
    }.onFailure {
        Log.e(tag, "SendImageUseCase error: ${it.message}")
    }
}