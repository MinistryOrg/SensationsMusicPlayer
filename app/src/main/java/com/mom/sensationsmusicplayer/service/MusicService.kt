package com.mom.sensationsmusicplayer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer()
    }

    fun playSong(context : Context, songUri : Uri){
        mediaPlayer?.setDataSource(context, songUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    fun stopSong(){
        mediaPlayer?.stop()
    }


}