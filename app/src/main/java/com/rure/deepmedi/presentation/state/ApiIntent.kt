package com.rure.deepmedi.presentation.state

import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserData
import com.rure.deepmedi.presentation.model.Attribute
import java.io.File

sealed class ApiIntent {
    data class SendImage(val image: File): ApiIntent()
    data class RetrieveUserAttr(val userData: UserData): ApiIntent()
}