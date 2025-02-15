package com.rure.deepmedi.data.repository

import android.util.Log
import com.rure.deepmedi.data.entity.AttributeTag
import com.rure.deepmedi.data.entity.Entity
import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserAttribute
import com.rure.deepmedi.data.entity.UserData
import com.rure.deepmedi.data.source.ApiDataSource
import com.rure.deepmedi.domain.repository.RetrofitApiRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class RetrofitApiRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource
): RetrofitApiRepository {
    private val tag = "RetrofitApiRepositoryImpl"
    override suspend fun uploadImageFile(file: MultipartBody.Part): Result<UserData> {
        return apiDataSource.uploadImageFile(file)
    }

    override suspend fun userAuthentication(loginId: String, password: String): Result<TokenList> {
        return apiDataSource.userAuthentication(loginId, password)
    }

    override suspend fun getUserInformation(token: String): Result<Entity> {
        return apiDataSource.getUserInformation(token)
    }

    override suspend fun retrieveUserAttribute(token: String, userId: String): Result<Map<AttributeTag, UserAttribute>> {
        return  kotlin.runCatching {
            val list = apiDataSource.retrieveUserAttribute(token, userId).getOrThrow()

            mutableMapOf<AttributeTag, UserAttribute>().apply {
                list.forEach {
                    put(AttributeTag.getFromTag(it.key)!!, it)
                }
            }
        }.onFailure {
            Log.e(tag, "fail to retrieveUserAttribute) ${it.message}")
            it.printStackTrace()
            throw it
        }
    }

}