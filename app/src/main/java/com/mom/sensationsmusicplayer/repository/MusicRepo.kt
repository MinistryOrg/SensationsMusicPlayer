package com.mom.sensationsmusicplayer.repository

import android.content.Context
import com.mom.sensationsmusicplayer.data.Song

interface MusicRepo {
    fun getSongs(context: Context) : List <Song>
}