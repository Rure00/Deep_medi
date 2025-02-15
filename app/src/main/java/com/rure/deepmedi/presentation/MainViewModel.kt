package com.rure.deepmedi.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.deepmedi.data.entity.AttributeTag
import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserAttribute
import com.rure.deepmedi.data.entity.UserData
import com.rure.deepmedi.domain.repository.RetrofitApiRepository
import com.rure.deepmedi.domain.usercase.GetAttributeUseCase
import com.rure.deepmedi.domain.usercase.SendImageUseCase
import com.rure.deepmedi.presentation.model.Attribute
import com.rure.deepmedi.presentation.model.BirthAttr
import com.rure.deepmedi.presentation.model.BloodPressureAttr
import com.rure.deepmedi.presentation.model.GenderAttr
import com.rure.deepmedi.presentation.model.HeartRateAttr
import com.rure.deepmedi.presentation.state.ApiIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sendImageUseCase: SendImageUseCase,
    private val getAttributeUseCase: GetAttributeUseCase
): ViewModel() {
    private val tag = "MainViewModel"
    private val _userAttrState = MutableStateFlow<List<Attribute<*>>>(listOf())
    val userAttrState = _userAttrState.asStateFlow()

    fun emit(intent: ApiIntent) {
        when(intent) {
            is ApiIntent.SendImage -> {
                sendPicture(intent.image, intent.listener)
            }
            is ApiIntent.RetrieveUserAttr -> {
                retrieveUserAttr(intent.loginId, intent.password)
            }
        }
    }

    private fun sendPicture(image: File, resultListener: (UserData?) -> Unit) {
        viewModelScope.launch {
            sendImageUseCase.invoke(image)
                .onSuccess {
                    resultListener(it)
                }.onFailure {
                    resultListener(null)
                }
        }
    }

    private fun retrieveUserAttr(loginId: String, pwd: String) {
        viewModelScope.launch {
            getAttributeUseCase.invoke(UserData(loginId, pwd))
                .onSuccess { list ->
                    _userAttrState.value = list
                }.onFailure {
                    Log.e(tag, "retrieveUserAttr Error: ${it.message}")
                }
        }
    }
}