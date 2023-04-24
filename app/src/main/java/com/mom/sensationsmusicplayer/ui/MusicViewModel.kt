package com.mom.sensationsmusicplayer.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService

class MusicViewModel : ViewModel() {
    private var musicService : MusicService = MusicService()

    fun playSong(context: Context, song: Song) {
        musicService.playSong(context = context, song)
    }

    fun nextSong(context: Context, songList: List<Song>, song: Song) {
        musicService.nextSong(context = context, songList, song)
    }

    fun prevSong(context: Context, songList: List<Song>, song: Song) {
        musicService.prevSong(context, songList, song)
    }

    fun stopSong() {
        musicService.stopSong()
    }

    fun pauseSong(){
        musicService.pauseSong()
    }


}

