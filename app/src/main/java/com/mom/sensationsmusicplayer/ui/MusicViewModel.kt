package com.mom.sensationsmusicplayer.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService

class MusicViewModel : ViewModel() {
    private val musicService : MusicService = MusicService()
    var song : Song ?= null
    var songList : List  <Song> ?= null
    var context : Context ?= null

    fun playSong() {
        Log.d("PLAYING THE SONG ", song!!.title)
        musicService.playSong(context = context!!, song!!)
    }

    fun nextSong() {
        song = musicService.nextSong(context = context!!, songList!!, song!!)
    }

    fun prevSong() {
        song = musicService.prevSong(context!!, songList!!, song!!)
    }

    fun stopSong() {
        musicService.stopSong()
    }

    fun pauseSong(){
        musicService.pauseSong()
    }


}

