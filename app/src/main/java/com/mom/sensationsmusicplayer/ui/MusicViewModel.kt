package com.mom.sensationsmusicplayer.ui

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService
import com.mom.sensationsmusicplayer.service.MusicServiceCallback
import com.mom.sensationsmusicplayer.service.NotificationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.LinkedList


class MusicViewModel() : ViewModel(), MusicServiceCallback{
    private val musicService : MusicService = MusicService()
    private val notificationService : NotificationService = NotificationService()
    var song : Song ?= null
    var songList : List  <Song> ?= null
    var songQueue : LinkedList <Song> = LinkedList()
    var context : Context ?= null

    private var mediaPlayer: MediaPlayer? = null

    var isPlaying = false

    lateinit var updateSong : MutableStateFlow<Song>

    fun init(){
        updateSong = MutableStateFlow(song!!)
    }

    fun playSong() {
        musicService.playSong(context = context!!, songList!!,songQueue!!, song!!, this)
        notificationService.playingInTheBackground(context!!)
        isPlaying = true
    }

    fun nextSong() {
        updateSong.value = musicService.nextSong(context = context!!, songList!!,songQueue!!, song!!,this)
    }

    fun prevSong() {
        updateSong.value = musicService.prevSong(context!!, songList!!,songQueue!!, song!!,this)
    }

    fun stopSong() {
        isPlaying = false
        musicService.stopSong()
    }

    fun moveInTrack(position: Float){
        musicService.seekTo(position)
    }

    fun pauseSong(){
        isPlaying = false
        musicService.pauseSong()
    }

    fun getDuration() : Float {
        return musicService.getDuration()
    }

    override fun onSongCompleted(nextSong: Song): Song {
        updateSong.value = nextSong
        song = nextSong
        return nextSong
    }

    fun getFormat(): String {
        return musicService.formatTime()
    }

    private val _currentPositionFlow = MutableStateFlow(0)
    val currentPositionFlow: StateFlow<Int> = _currentPositionFlow.asStateFlow()

    fun getCurrentPosition(action: String): String {
        return musicService.getCurrentPosition(action);
    }

    fun addToQueue(song: Song){
        songQueue!!.add(song)
    }



}

