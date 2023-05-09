package com.mom.sensationsmusicplayer.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService
import com.mom.sensationsmusicplayer.service.MusicServiceCallback
import com.mom.sensationsmusicplayer.service.NotificationService
import kotlinx.coroutines.flow.MutableStateFlow

class MusicViewModel : ViewModel(), MusicServiceCallback  {
    private val musicService : MusicService = MusicService()
    private val notificationService : NotificationService = NotificationService()
    var song : Song ?= null
    var songList : List  <Song> ?= null
    var context : Context ?= null

    lateinit var updateSong : MutableStateFlow<Song>

    fun init(){
        updateSong = MutableStateFlow(song!!)
    }

    fun playSong() {
        //notificationService.showNotification(context = context!!, song!!)
        musicService.playSong(context = context!!,songList!!, song!!,this)
        notificationService.playingInTheBackground(context!!,updateSong.value,musicService)
    }

    fun nextSong() {
        updateSong.value = musicService.nextSong(context = context!!, songList!!, song!!,this)
    }

    fun prevSong() {
        updateSong.value = musicService.prevSong(context!!, songList!!, song!!,this)
    }

    fun stopSong() {
        musicService.stopSong()
    }

    fun moveInTrack(position: Float){
        musicService.seekTo(position)
    }

    fun pauseSong(){
        musicService.pauseSong()
    }

    fun getDuration() : String{
        return musicService.getDuration()
    }

    override fun onSongCompleted(nextSong: Song): Song {
        updateSong.value = nextSong
        song = nextSong
        return nextSong
    }
}

