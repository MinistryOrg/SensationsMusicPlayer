package com.mom.sensationsmusicplayer.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService
import com.mom.sensationsmusicplayer.service.NotificationService

class MusicViewModel : ViewModel() {
    private val musicService : MusicService = MusicService()
    private val notificationService : NotificationService = NotificationService()
    var song : Song ?= null
    var songList : List  <Song> ?= null
    var context : Context ?= null

    fun playSong() {
        //notificationService.showNotification(context = context!!, song!!)
        notificationService.playingInTheBackground(context!!,song!!,musicService)
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

    fun moveInTrack(position : Int){
        musicService.seekTo(position )
    }

    fun pauseSong(){
        musicService.pauseSong()
    }


}

