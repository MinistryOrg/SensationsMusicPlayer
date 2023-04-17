package com.mom.sensationsmusicplayer.repository

import android.content.Context
import com.mom.sensationsmusicplayer.data.Song

interface SongsRepo {
    fun getSongs(context: Context) : List <Song>
}