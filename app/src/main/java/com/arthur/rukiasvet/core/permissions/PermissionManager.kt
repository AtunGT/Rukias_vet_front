// core/permissions/PermissionManager.kt
package com.arthur.rukiasvet.core.permissions

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*

data class PermissionManager(
    val onRequestCamera: () -> Unit,
    val onRequestLocation: () -> Unit,
    val onRequestGallery: () -> Unit
)

@Composable
fun rememberPermissionManager(
    onCameraGranted: () -> Unit,
    onLocationGranted: () -> Unit,
    onGalleryGranted: () -> Unit,
    onDenied: (String) -> Unit = {}
): PermissionManager {

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onCameraGranted()
        else onDenied("Permiso de cámara denegado")
    }

    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) onLocationGranted()
        else onDenied("Permiso de ubicación denegado")
    }

    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onGalleryGranted()
        else onDenied("Permiso de galería denegado")
    }

    return PermissionManager(
        onRequestCamera = { cameraLauncher.launch(Manifest.permission.CAMERA) },
        onRequestLocation = {
            locationLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        },
        onRequestGallery = { galleryLauncher.launch(galleryPermission) }
    )
}