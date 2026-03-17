package com.arthur.rukiasvet.core.hardware.camera

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.arthur.rukiasvet.core.hardware.storage.FileRepository
import com.arthur.rukiasvet.core.permissions.rememberPermissionManager
import java.io.File

data class CameraManager(
    val capturedFile: File?,
    val onTakePicture: () -> Unit,
    val onPickFromGallery: () -> Unit,
    val onClear: () -> Unit
)

@Composable
fun rememberCameraManager(
    cameraRepository: CameraRepository,
    fileRepository: FileRepository,
    onImageReady: (File) -> Unit
): CameraManager {
    val context = LocalContext.current

    var tempFilePath by rememberSaveable { mutableStateOf<String?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempFilePath?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    onImageReady(file)
                }
            }
        } else {
            tempFilePath?.let { File(it).delete() }
            tempFilePath = null
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val file = fileRepository.copyUriToFile(context, it)
            onImageReady(file)
        }
    }

    val permissionManager = rememberPermissionManager(
        onCameraGranted = {
            val file = cameraRepository.createTempImageFile(context)
            val uri = cameraRepository.getUriForFile(context, file)
            tempFilePath = file.absolutePath  
            cameraLauncher.launch(uri)
        },
        onGalleryGranted = { galleryLauncher.launch("image/*") },
        onLocationGranted = {}
    )

    return CameraManager(
        capturedFile = tempFilePath?.let { File(it) },
        onTakePicture = { permissionManager.onRequestCamera() },
        onPickFromGallery = { permissionManager.onRequestGallery() },
        onClear = {
            tempFilePath?.let { File(it).delete() }
            tempFilePath = null
        }
    )
}