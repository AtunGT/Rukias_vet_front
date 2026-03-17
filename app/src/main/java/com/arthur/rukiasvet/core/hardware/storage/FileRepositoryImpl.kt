package com.arthur.rukiasvet.core.hardware.storage

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepositoryImpl @Inject constructor() : FileRepository {

    override fun copyUriToFile(context: Context, uri: Uri): File {
        val contentResolver: ContentResolver = context.contentResolver
        val mimeType = contentResolver.getType(uri)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
        val fileName = "temp_${System.currentTimeMillis()}.$extension"
        val cacheFile = File(context.cacheDir, fileName)

        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(cacheFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return cacheFile
    }

    override fun deleteFile(file: File): Boolean = file.delete()

    override fun getCacheDir(context: Context): File = context.cacheDir
}