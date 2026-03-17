package com.arthur.rukiasvet.core.hardware.camera

import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import com.arthur.rukiasvet.core.hardware.camera.model.CameraResult
import java.io.File

interface CameraRepository {
    fun createTempImageFile(context: Context): File
    fun getUriForFile(context: Context, file: File): Uri
}