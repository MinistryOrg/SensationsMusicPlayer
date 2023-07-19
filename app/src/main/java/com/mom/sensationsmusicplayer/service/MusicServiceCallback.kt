/*
* Χρήστος Κόκκαλης, 19390090
* Παναγιώτης Γεωργίου, 19390260
* */
package com.mom.sensationsmusicplayer.service

import com.mom.sensationsmusicplayer.data.Song

interface MusicServiceCallback {
    fun onSongCompleted(nextSong: Song) : Song
}