package com.rure.deepmedi.domain.usercase

import android.util.Log
import com.rure.deepmedi.data.entity.AttributeTag
import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserData
import com.rure.deepmedi.domain.repository.RetrofitApiRepository
import com.rure.deepmedi.presentation.model.Attribute
import com.rure.deepmedi.presentation.model.BirthAttr
import com.rure.deepmedi.presentation.model.BloodPressureAttr
import com.rure.deepmedi.presentation.model.GenderAttr
import com.rure.deepmedi.presentation.model.HeartRateAttr
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAttributeUseCase @Inject constructor(
    private val retrofitApiRepository: RetrofitApiRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val tag = "GetAttributeUseCase"

    suspend operator fun invoke(userData: UserData) = kotlin.runCatching {
        withContext(ioDispatcher) {
            val tokenList = retrofitApiRepository.userAuthentication(userData.email, userData.password).getOrThrow()
            val userId = retrofitApiRepository.getUserInformation(tokenList.token).getOrThrow()

            retrofitApiRepository.retrieveUserAttribute(tokenList.token, userId.id).getOrThrow().map {
                when(it.key) {
                    AttributeTag.Birth -> BirthAttr(it.value.value, it.key, it.value.lastUpdateTs.toLong())
                    AttributeTag.BloodPressure -> BloodPressureAttr(it.value.value, it.key, it.value.lastUpdateTs.toLong())
                    AttributeTag.Gender -> GenderAttr(it.value.value.toInt(), it.key, it.value.lastUpdateTs.toLong())
                    AttributeTag.HeartRate -> HeartRateAttr(it.value.value, it.key, it.value.lastUpdateTs.toLong())
                }
            }.toList()
        }
    }.onFailure {
        Log.e(tag, "GetAttributeUseCase error: ${it.message}")
    }
}