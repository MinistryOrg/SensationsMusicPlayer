package com.mom.sensationsmusicplayer.ui

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {

    private var mediaPlayer: MediaPlayer? = null

    fun playSong(context: Context, songUri: Uri) {

        if (mediaPlayer == null) {
            println("media player is null")
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }

        mediaPlayer?.setDataSource(context, songUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    fun stopSong() {
        mediaPlayer?.stop()
    }

    fun playingInTheBackground(){

    }

    override fun onCleared() {
        mediaPlayer?.release()
        super.onCleared()
    }
}