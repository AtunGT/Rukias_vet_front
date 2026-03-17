package com.arthur.rukiasvet.core.hardware.camera.model

import android.net.Uri
import java.io.File

sealed class CameraResult {
    data class Success(val file: File, val uri: Uri) : CameraResult()
    data class Error(val message: String, val throwable: Throwable? = null) : CameraResult()
    object Cancelled : CameraResult()
}