package com.mom.sensationsmusicplayer.service

import com.mom.sensationsmusicplayer.data.Song

interface MusicServiceCallback {
    fun onSongCompleted(nextSong: Song) : Song
}