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
    var stopPlaying = false

    lateinit var updateSong : MutableStateFlow<Song>

    fun init(){
        updateSong = MutableStateFlow(song!!)
    }

    fun playSong() {
        musicService.playSong(context = context!!, songList!!, songQueue, song!!, this)
        notificationService.playingInTheBackground(context!!)
        isPlaying = true
        stopPlaying = false
    }

    fun nextSong() {
        updateSong.value = musicService.nextSong(context = context!!, songList!!,
            songQueue, song!!,this)
        isPlaying = true
        stopPlaying = false
    }

    fun prevSong() {
        updateSong.value = musicService.prevSong(context!!, songList!!, songQueue, song!!,this)
        isPlaying = true
        stopPlaying = false
    }

    fun stopSong() {
        musicService.stopSong()
        isPlaying = false
        stopPlaying = true
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

    fun getCurrentPosition(action: String): String {
        return musicService.getCurrentPosition(action);
    }

    fun addToQueue(song: Song){
        songQueue.add(song)
    }

}

