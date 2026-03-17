package com.arthur.rukiasvet.core.hardware.camera

sealed class CameraAction {
    object TakePicture : CameraAction()
    object PickFromGallery : CameraAction()
}