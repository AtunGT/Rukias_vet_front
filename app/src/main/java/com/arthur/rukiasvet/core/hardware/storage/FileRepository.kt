package com.arthur.rukiasvet.core.hardware.storage

import android.content.Context
import android.net.Uri
import java.io.File

interface FileRepository {
    fun copyUriToFile(context: Context, uri: Uri): File
    fun deleteFile(file: File): Boolean
    fun getCacheDir(context: Context): File
}