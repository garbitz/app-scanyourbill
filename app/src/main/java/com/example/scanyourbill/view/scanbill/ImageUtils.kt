package com.example.scanyourbill.view.scanbill

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object ImageUtils {
    private const val BASE64_IMAGE_PREFIX = "data:image/jpeg;base64,"

    fun uriToBase64(context: Context, uri: Uri): String {
        var inputStream: InputStream? = null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            byteArrayOutputStream = ByteArrayOutputStream()
            // Compress bitmap to JPEG format with adjustable quality
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            Log.d("this: ", BASE64_IMAGE_PREFIX + base64String)
            return BASE64_IMAGE_PREFIX + base64String
        } catch (e: IOException) {
            // Handle exception, e.g., log or throw custom exception
            throw IOException("Error converting URI to Base64", e)
        } finally {
            // Close resources in finally block
            try {
                inputStream?.close()
                byteArrayOutputStream?.close()
            } catch (e: IOException) {
                // Log error closing streams
                Log.e("ImageUtils", "Error closing streams", e)
            }
        }
    }
}
