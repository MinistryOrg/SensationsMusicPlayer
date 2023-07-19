/*
* Χρήστος Κόκκαλης, 19390090
* Παναγιώτης Γεωργίου, 19390260
* */
package com.mom.sensationsmusicplayer.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Utill {
    suspend fun loadAlbumArtBitmap(uri: String, context: Context): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                // loading the album art bitmap from the album art URI
                val inputStream = context.contentResolver.openInputStream(Uri.parse(uri))
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                null
            }
        }
    }
}