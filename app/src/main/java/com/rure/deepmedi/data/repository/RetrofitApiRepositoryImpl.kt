package com.rure.deepmedi.data.repository

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
    override suspend fun uploadImageFile(file: MultipartBody.Part): Result<UserData> {
        return apiDataSource.uploadImageFile(file)
    }

    override suspend fun userAuthentication(loginId: String, password: String): Result<TokenList> {
        return apiDataSource.userAuthentication(loginId, password)
    }

    override suspend fun getUserInformation(token: String): Result<Entity> {
        return apiDataSource.getUserInformation(token)
    }

    override suspend fun retrieveUserAttribute(token: String, userId: String): Result<List<UserAttribute>> {
        return  apiDataSource.retrieveUserAttribute(token, userId)
    }

}