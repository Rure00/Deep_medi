package com.rure.deepmedi.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.deepmedi.data.entity.TokenList
import com.rure.deepmedi.data.entity.UserAttribute
import com.rure.deepmedi.data.entity.UserData
import com.rure.deepmedi.domain.repository.RetrofitApiRepository
import com.rure.deepmedi.domain.usercase.GetAttributeUseCase
import com.rure.deepmedi.presentation.model.Attribute
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
    private val repository: RetrofitApiRepository,
    private val getAttributeUseCase: GetAttributeUseCase
): ViewModel() {

    private val _userDataState = MutableStateFlow<UserData?>(null)
    val userDataState = _userDataState.asStateFlow()

    private val _tokenListState = MutableStateFlow<TokenList?>(null)
    val tokenListState = _tokenListState.asStateFlow()

    private val _userAttrState = MutableStateFlow<List<Attribute<*>>>(listOf())
    val userAttrState = _userAttrState.asStateFlow()

    fun emit(intent: ApiIntent) {
        when(intent) {
            is ApiIntent.SendImage -> {
                sendPicture(intent.image)
            }
            is ApiIntent.TryAuthentication -> {
                tryAuthenticate(intent.userData)
            }
            is ApiIntent.GetUserInformation -> {
                getUserInfo(intent.token)
            }
            is ApiIntent.RetrieveUserAttr -> {
                retrieveUserAttr(intent.userId)
            }
        }
    }

    private fun sendPicture(image: File) {
        viewModelScope.launch {
            val fileBody = RequestBody.create("image/*".toMediaTypeOrNull(), image)
            val part = MultipartBody.Part.createFormData("file", image.name, fileBody)
            val result = repository.uploadImageFile(part)

            result.onSuccess {
                Log.d("MainViewModel", "sendPicture Success) ${it.email}, ${it.password}")
            }.onFailure {
                Log.d("MainViewModel", "sendPicture Fail) ${it.message}")
            }
        }
    }
    private fun tryAuthenticate(userData: UserData) {
        viewModelScope.launch {

        }
    }
    private fun getUserInfo(tokenList: TokenList) {
        viewModelScope.launch {

        }
    }

    private fun retrieveUserAttr(userId: String) {
        viewModelScope.launch {
            tokenListState.value?.let {
                getAttributeUseCase.invoke(it, userId)
                    .onSuccess { list ->
                        _userAttrState.value = list
                    }
            }

        }
    }
}