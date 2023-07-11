package com.mom.sensationsmusicplayer.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.mom.sensationsmusicplayer.data.Song

class MusicRepoImpl : MusicRepo {
    override fun getSongs(context: Context): List<Song> {
        val songsList = mutableListOf<Song>()
        // retrieve the uri for accessing the audio files
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        //  columns to retrieve from the songs
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
        )
        // sorting order for the song list
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"
        // uri path for retrieving album art
        val albumArtUri = Uri.parse("content://media/external/audio/albumart")
        // query to return the music from the path i want and the way i want
        context.contentResolver.query(uri, projection, null, null, sortOrder)?.use { cursor ->
            // retrieve the column  for the required data
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumColumns = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            // iterate through the cursor to retrieve song data
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val duration = cursor.getLong(durationColumn)
                val album = cursor.getString(albumColumns)
                // append the id of the album to get the album cover
                val albumCover = ContentUris.withAppendedId(albumArtUri, albumId)
                // create a uri for accessing the song
                val songUri =
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                // create song object
                val song = Song(id, title, artist, album, albumCover.toString(), duration, songUri)

                songsList.add(song)
            }
        }
        return songsList
    }
}

