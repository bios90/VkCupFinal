package com.example.vkcupfinal.utils.files

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import java.io.*
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import java.util.*
import com.example.vkcupfinal.base.AppClass
import com.example.vkcupfinal.base.Consts
import com.example.vkcupfinal.base.ext.formatToString
import com.example.vkcupfinal.base.ext.safe
import com.example.vkcupfinal.utils.DateManager
import java.net.URLConnection

object FileManager {

    fun createTempImageFile(extension: String = Consts.EXTENSION_PNG): File {
        val name = getNameForNewFile(extension)
        return createFile(name, null, Consts.FOLDER_TEMP_FILES)
    }

    fun createTempVideoFile(extension: String = Consts.EXTENSION_MP4): File {
        val name = getNameForNewFile(extension)
        return createFile(name, null, Consts.FOLDER_TEMP_FILES)
    }

    private fun createFile(name: String, extansion: String?, folderName: String): File {
        val folder = File(getRootFolder() + "/" + folderName)
        if (folder.exists().not()) {
            folder.mkdirs()
        }

        var fileName = name
        if (extansion != null && extansion.length > 0) {
            fileName = fileName + "." + extansion
        }

        val file = File(folder, fileName)
        if (file.exists()) {
            file.delete()
        }

        file.createNewFile()

        return file
    }

    fun getRootFolder(): String {
        return AppClass.app.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()
    }

    fun getNameForNewFile(extension: String): String {
        var name = Date().formatToString(DateManager.FORMAT_FILE_NAME)
        name += ".$extension"
        return name
    }

    fun isContentImage(uri: Uri): Boolean {
        val cR = AppClass.app.getContentResolver()
        val type = cR.getType(uri)
        return type?.startsWith("image").safe()
    }

    fun getExtensionFromContentUri(uri: Uri): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(AppClass.app.contentResolver.getType(uri))
        } else {
            val file = uri.path?.let { File(it) } ?: return null
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        }
    }

    fun copyToFileFromIntentData(file: File, data: Intent) {
        val d = data.data ?: return
        val inputStream = AppClass.app.contentResolver.openInputStream(d)
        val outputStream = FileOutputStream(file)
        inputStream ?: return
        copy(inputStream, outputStream)
    }


    fun copy(input: InputStream, output: OutputStream) {
        try {
            val buf = ByteArray(1024)
            var len: Int = input.read(buf)

            while (len > 0) {
                output.write(buf, 0, len)
                len = input.read(buf)
            }
        } catch (e: java.lang.Exception) {
        } finally {
            try {
                input.close()
            } catch (ioex: IOException) {
                Log.e("FileManager", "Exception : " + ioex.message);
            }

            try {
                output.close()
            } catch (ioex: IOException) {
                Log.e("FileManager", "Exception : " + ioex.message);
            }
        }
    }

    fun isFileVideo(pathReal: String): Boolean {
        val mimeType = URLConnection.guessContentTypeFromName(pathReal)
        return mimeType.startsWith("video")
    }

    fun isFileImage(pathReal: String): Boolean {
        val mimeType = URLConnection.guessContentTypeFromName(pathReal)
        return mimeType.startsWith("image")
    }

    fun getUriFileName(uri: Uri): String? {
        var cursor: Cursor? = null
        var name: String? = null
        try {
            cursor = AppClass.app.contentResolver.query(uri, null, null, null, null)!!
            val nameIndex: Int = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            name = cursor.getString(nameIndex)
            return name
        } catch (e: Exception) {
        } finally {
            cursor?.close()
        }

        return name
    }

    fun saveBitmapToFile(bitmap: Bitmap): File? {
        try {
            val file = createTempImageFile()
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            return file
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
