package com.rure.deepmedi.presentation.camera

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rure.deepmedi.MainActivity
import com.rure.deepmedi.R
import com.rure.deepmedi.presentation.MainViewModel
import com.rure.deepmedi.presentation.component.LoadingDialog
import com.rure.deepmedi.presentation.state.ApiIntent
import com.rure.deepmedi.presentation.utils.MyCameraX
import com.rure.deepmedi.ui.theme.Gray
import com.rure.deepmedi.utils.matchRatioToWidth
import com.rure.deepmedi.utils.toDesignDp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    toHome: (String, String) -> Unit,
    context: Context = LocalContext.current,
    mainViewModel: MainViewModel = hiltViewModel()
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
    ) { permissionToIsGranted ->
        if(!permissionToIsGranted.containsValue(false)) {
            cameraX.initialize(context = context)
            previewView.value = cameraX.getPreviewView()

            cameraScope.launch(Dispatchers.Main) {
                cameraX.startCamera(lifecycleOwner = lifecycleOwner)
            }
        } else {
            Toast.makeText(context, context.getString(R.string.need_permission_guide), Toast.LENGTH_SHORT).show()
        }
    }

    var doTakingPicture by remember { mutableStateOf(false) }

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


        if(!permissions.allPermissionsGranted) {
            return@Box
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 305.matchRatioToWidth(context))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth().aspectRatio(1f)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(11.toDesignDp()),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().aspectRatio(1f)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            width = 2.dp,
                            color = Gray,
                            shape = CircleShape
                        ).clickable(
                            indication = rememberRipple(bounded = true, color = Gray),
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            doTakingPicture = true
                            cameraX.takePicture { name ->
                                val imageFile = cameraX.getImage(name)
                                if(imageFile != null) {
                                    mainViewModel.emit(
                                        ApiIntent.SendImage(imageFile) { result ->
                                            if(result != null) toHome(result.email, result.password)
                                            else
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.fail_sending_image_guide),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            doTakingPicture = false
                                        }
                                    )

                                } else {
                                    Toast.makeText(context, context.getString(R.string.fail_taking_picture_guide), Toast.LENGTH_SHORT).show()
                                    doTakingPicture = false
                                }
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(33.toDesignDp()))
        }
    }

    if(doTakingPicture) {
        LoadingDialog()
    }


}