package com.example.vkcupfinal.utils.files

import android.net.Uri
import java.io.File
import android.content.Intent
import com.example.vkcupfinal.base.Consts
import com.example.vkcupfinal.data.ObjectWithImageUrl
import com.example.vkcupfinal.data.ObjectWithVideoUrl
import okhttp3.MultipartBody
import java.io.Serializable
import java.lang.RuntimeException

data class FileItem(
    private var uri: String,
    val originalFileName: String? = null
) : Serializable, ObjectWithImageUrl, ObjectWithVideoUrl {
    override var imageUrl: String? = uri
    override var videoUrl: String? = uri


    companion object {
        fun createFromData(data: Intent): FileItem {
            val file: File
            val resultData = data.data ?: throw RuntimeException("**** Error null data passed ****")
            if (FileManager.isContentImage(resultData)) {
                val extension = FileManager.getExtensionFromContentUri(resultData)
                file = FileManager.createTempImageFile(extension ?: "")
            } else {
                val extension =
                    FileManager.getExtensionFromContentUri(data.data!!) ?: Consts.EXTENSION_MP4
                file = FileManager.createTempVideoFile(extension)
            }

            FileManager.copyToFileFromIntentData(file, data)
            val myFile = FileItem(
                uri = file.absolutePath,
                originalFileName = FileManager.getUriFileName(resultData)
            )

            return myFile
        }

        fun createFromFile(file: File): FileItem {
            return FileItem(
                uri = file.absolutePath,
                originalFileName = file.name
            )
        }
    }

    fun getUri(): Uri {
        return Uri.parse(uri)
    }

    fun getUriAsString(): String {
        return uri
    }

    fun getFile(): File {
        return File(getUri().path)
    }

    fun isImage(): Boolean {
        return FileManager.isFileImage(uri)
    }

    fun isVideo(): Boolean {
        return FileManager.isFileVideo(uri)
    }

    fun getSize(): Long {
        return getFile().length()
    }

    fun getSizeInMb(): Double {
        return getFile().length().toDouble() / (1024 * 1024).toDouble()
    }

    fun getSizeString(): String {
        return String.format("%.1f mb", getSizeInMb())
    }
}


