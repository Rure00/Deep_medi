package com.rure.deepmedi.presentation.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.takePicture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyCameraX  {
    private val tag = "MyCameraX"


    private val _facing = MutableStateFlow(CameraSelector.LENS_FACING_BACK)

    private val path = Environment.DIRECTORY_PICTURES + "/DeepMedi"

    private lateinit var previewView: PreviewView
    private lateinit var preview: Preview
    private lateinit var cameraProvider: ListenableFuture<ProcessCameraProvider>
    private lateinit var provider: ProcessCameraProvider
    private lateinit var camera: Camera
    private lateinit var context: Context
    private lateinit var executor : ExecutorService

    private lateinit var imageCapture : ImageCapture

    fun initialize(context: Context) {
        previewView = PreviewView(context)
        preview = Preview.Builder().build().also { it.surfaceProvider = previewView.surfaceProvider }
        cameraProvider = ProcessCameraProvider.getInstance(context)
        provider = cameraProvider.get()
        imageCapture = ImageCapture.Builder().build()
        executor = Executors.newSingleThreadExecutor()
        this.context = context
    }

    fun startCamera(
        lifecycleOwner: LifecycleOwner,
    ) {
        unBindCamera()

        val cameraSelector = CameraSelector.Builder().requireLensFacing(_facing.value).build()

        cameraProvider.addListener({
            CoroutineScope(Dispatchers.Main).launch {
                camera = provider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                Log.d(tag, "set camera")
            }
        },executor)
    }

    fun takePicture(
        onSave: (String) -> Unit
    ) {
        val photoName = "IMG_${System.currentTimeMillis()}.jpg"


        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, photoName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, path)
        }

        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {

            imageCapture.takePicture(
                outputFileOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(error: ImageCaptureException) {
                        error.printStackTrace()
                    }
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                        Log.d(tag, "Capture Success!!) uri: ${path}/$photoName")
                        Log.d(tag, "uri: ${outputFileResults.savedUri}")
                        onSave(photoName)
                    }
                }
            )
        }
    }

    fun getImage(photoName: String): File? {
        try {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(photoName)

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    val filePath = cursor.getString(dataColumn)

                    return File(filePath)
                }
            }

            return null
        } catch (e: Exception) {
            Log.e(tag, "getImage Failed: ${e.message}")
            return null
        }
    }

    fun deleteImage(uri: Uri): Boolean {
        return try {
            val rowsDeleted = context.contentResolver.delete(uri, null, null)
            rowsDeleted > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun unBindCamera() {
        provider.unbindAll()
    }

    fun getPreviewView(): PreviewView = previewView
    fun getFacingState(): StateFlow<Int> = _facing.asStateFlow()

}