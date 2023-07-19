/*
* Χρήστος Κόκκαλης, 19390090
* Παναγιώτης Γεωργίου, 19390260
* */
package com.mom.sensationsmusicplayer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.mom.sensationsmusicplayer.data.Song
import java.util.LinkedList
import java.util.concurrent.TimeUnit

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var pausedPosition: Int = 0

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer()
    }

    fun playSong(
        context: Context,
        songList: List<Song>,
        songQueue: LinkedList<Song>,
        song: Song,
        musicServiceCallback: MusicServiceCallback
    ) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }
        // set the data source for the media player
        mediaPlayer?.setDataSource(context, song.songUri)
        mediaPlayer?.prepare()
        // to resume the song from where it left if the user pause it
        if (pausedPosition > 0) {
            mediaPlayer?.seekTo(pausedPosition) // goes to the previous position
            pausedPosition = 0 // reset the position to zero so if the user go to the next song to start from the beg
        }

        mediaPlayer?.start() /// start playing the song
        // when a song finish
        mediaPlayer?.setOnCompletionListener {
            musicServiceCallback.onSongCompleted(
                nextSong( // go to the next song when the curr song is finish
                    context,
                    songList,
                    songQueue,
                    song,
                    musicServiceCallback
                )
            )
        }

    }

    // play the next song in the queue or the next song in the song list
    fun nextSong(
        context: Context,
        songList: List<Song>,
        songQueue: LinkedList<Song>,
        song: Song,
        musicServiceCallback: MusicServiceCallback
    ): Song {
        if (songQueue.isEmpty()) { // if the song queue is empty, play the next song in the list
            val songPos = songList.indexOf(song) // find the position of the current song
            val newSongPos = songPos + 1 // set a new position for the next song
            if (songPos != -1 && newSongPos < songList.size) { // check if get out of bounds
                val nextSong = songList[newSongPos] // set the next song
                playSong(context, songList, songQueue, nextSong, musicServiceCallback) // play the next song
                return nextSong
            }
        } else { // play the song that is in the queue
            val songQ: Song = songQueue.pollFirst()!! // remove from the queue the first song that is added
            playSong(context, songList, songQueue, songQ, musicServiceCallback) // play the first song in the queue
            return songQ
        }
        return song
    }

    fun prevSong(
        context: Context,
        songList: List<Song>,
        songQueue: LinkedList<Song>,
        song: Song,
        musicServiceCallback: MusicServiceCallback
    ): Song {
        val songPos = songList.indexOf(song)
        val prevSongPos = songPos - 1
        if (prevSongPos >= 0) {
            val prevSong = songList[prevSongPos]
            playSong(context, songList, songQueue, prevSong, musicServiceCallback)
            return prevSong
        }
        return song
    }

    fun pauseSong() {
        if (mediaPlayer?.isPlaying == true) { // check if the song is playing then pause it
            mediaPlayer?.pause()
            pausedPosition = mediaPlayer?.currentPosition ?: 0
        }
    }

    fun stopSong() {
        mediaPlayer?.stop()
    }

    //  to move in track
    fun seekTo(position: Float) {
        mediaPlayer?.seekTo(convertMinToMilli(position)) // position is in milliseconds
    }

    private fun convertMinToMilli(position: Float): Int {
        val milli = position * 1000
        return milli.toInt()
    }

    private fun convertMilli(duration: Int): Float {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) - TimeUnit.MINUTES.toSeconds(minutes)
        return (minutes * 60 + seconds).toFloat()
    }

    fun getCurrentPosition(action: String): String {
        when (action) {
            "slider" -> return convertMilli(mediaPlayer?.currentPosition ?: 0).toString()
            "time" -> return formatTimeSlider(mediaPlayer?.currentPosition ?: 0)
        }
        return ""
    }

    fun getDuration(): Float {
        return convertMilli(mediaPlayer?.duration ?: 0)
    }

    fun formatTime(): String {
        val totalSeconds = ((mediaPlayer?.duration ?: 0) / 1000)
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun formatTimeSlider(duration: Int): String {
        val totalSeconds = (duration / 1000)
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

}
