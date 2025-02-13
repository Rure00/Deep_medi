package com.rure.deepmedi.presentation.state

import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserData
import java.io.File

sealed class ApiIntent {
    data class SendImage(val image: File): ApiIntent()
    data class TryAuthentication(val userData: UserData): ApiIntent()
    data class GetUserInformation(val token: TokenList): ApiIntent()
    data class RetrieveUserAttr(val userId: String): ApiIntent()
}