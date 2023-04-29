package com.mom.sensationsmusicplayer.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService

class MusicViewModel : ViewModel() {
    private val musicService : MusicService = MusicService()
    var song : Song ?= null
    var songList : List  <Song> ?= null
    var context : Context ?= null

    fun playSong() {
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

