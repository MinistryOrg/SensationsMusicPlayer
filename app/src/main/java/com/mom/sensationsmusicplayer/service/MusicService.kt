package com.mom.sensationsmusicplayer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.mom.sensationsmusicplayer.data.Song

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var notificationService: NotificationService? = null
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer()
    }

    fun playSong(context: Context, song: Song) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }
        mediaPlayer?.setDataSource(context, song.songUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
        //seekTo(60000) testing is in milliseconds 60000 is one minute
    }

    fun nextSong(context: Context, songList: List<Song>, song: Song): Song {
        val songPos = songList.indexOf(song)
        val newSongPos = songPos + 1
        if (songPos != -1 && newSongPos < songList.size) {
            val nextSong = songList[newSongPos]
            playSong(context, nextSong)
            return nextSong
        }
        return song
    }

    fun prevSong(context: Context, songList: List<Song>, song: Song) : Song {
        val songPos = songList.indexOf(song)
        val nextSongPos = songPos - 1
        if (nextSongPos > 0) {
            val prevSong = songList[nextSongPos]
            playSong(context, prevSong)
            return prevSong
        }
        return  song
    }

    fun pauseSong(){
        mediaPlayer?.pause()
    }

    fun stopSong() {
        mediaPlayer?.stop()
    }

    //  to move in track
    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position) // position is in milliseconds
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

}