package com.rure.deepmedi.presentation.camera

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraX
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.rure.deepmedi.utils.designDp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.rure.deepmedi.MainActivity
import com.rure.deepmedi.R
import com.rure.deepmedi.data.entity.UserData
import com.rure.deepmedi.presentation.CameraViewModel
import com.rure.deepmedi.presentation.MainViewModel
import com.rure.deepmedi.presentation.state.ApiIntent
import com.rure.deepmedi.presentation.utils.MyCameraX
import com.rure.deepmedi.ui.theme.Gray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    toHome: () -> Unit,
    context: Context = LocalContext.current,
    mainViewModel: MainViewModel = viewModel(context as MainActivity)
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraScope = rememberCoroutineScope()

    val cameraX by remember {
        mutableStateOf<MyCameraX>(MyCameraX())
    }
    val previewView = remember { mutableStateOf<PreviewView?>(null) }
    val facing = cameraX.getFacingState().collectAsState()

    val permissions = rememberMultiplePermissionsState(
        listOf(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    ) {
        cameraX.initialize(context = context)
        previewView.value = cameraX.getPreviewView()

        cameraScope.launch(Dispatchers.Main) {
            cameraX.startCamera(lifecycleOwner = lifecycleOwner)
        }
    }

    val rippleScope = rememberCoroutineScope()
    var doTakingPicture by remember { mutableStateOf(false) }
    fun rippleScreen() {
        rippleScope.launch {
            doTakingPicture = true
            delay(500L)
            doTakingPicture = false
        }
    }

    LaunchedEffect(Unit) {
        if(permissions.allPermissionsGranted) {
            cameraX.initialize(context = context)
            previewView.value = cameraX.getPreviewView()

            cameraScope.launch(Dispatchers.Main) {
                cameraX.startCamera(lifecycleOwner = lifecycleOwner)
            }
        } else {
            permissions.launchMultiplePermissionRequest()
        }
    }

    DisposableEffect(facing.value) {
        onDispose {
            cameraX.unBindCamera()
        }
    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        previewView.value?.let { preview ->
            AndroidView(modifier = Modifier.fillMaxSize(), factory = { preview }) {}
        }
        Column {
            Box(
                modifier = Modifier
                    .size(55.designDp())
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(44.designDp())
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = 1.designDp(),
                            color = Gray,
                            shape = CircleShape
                        ).clickable(
                            indication = rememberRipple(bounded = true, color = Gray),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            rippleScreen()
                            cameraX.takePicture { name ->
                                val imageFile = cameraX.getImage(name)
                                if(imageFile != null) {
                                    mainViewModel.emit(ApiIntent.SendImage(imageFile))
                                    toHome()
                                } else {
                                    Toast.makeText(context, context.getString(R.string.fail_taking_picture_guide), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(33.designDp()))
        }

    }

    if(doTakingPicture) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7f))
        )
    }


}