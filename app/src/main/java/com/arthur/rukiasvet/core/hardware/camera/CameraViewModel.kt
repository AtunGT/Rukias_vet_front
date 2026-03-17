package com.arthur.rukiasvet.core.hardware.camera

import androidx.lifecycle.ViewModel
import com.arthur.rukiasvet.core.hardware.storage.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    val cameraRepository: CameraRepository,
    val fileRepository: FileRepository
) : ViewModel()
