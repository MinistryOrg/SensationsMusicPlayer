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

        mediaPlayer?.setDataSource(context, song.songUri)
        mediaPlayer?.prepare()

        if (pausedPosition > 0) {
            mediaPlayer?.seekTo(pausedPosition)
            pausedPosition = 0
        }

        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            musicServiceCallback.onSongCompleted(
                nextSong(
                    context,
                    songList,
                    songQueue,
                    song,
                    musicServiceCallback
                )
            )
        }
        //seekTo(60000) testing is in milliseconds 60000 is one minute
    }


    fun nextSong(
        context: Context,
        songList: List<Song>,
        songQueue: LinkedList<Song>,
        song: Song,
        musicServiceCallback: MusicServiceCallback
    ): Song {
        if (songQueue.isEmpty()) {
            val songPos = songList.indexOf(song)
            val newSongPos = songPos + 1
            if (songPos != -1 && newSongPos < songList.size) {
                val nextSong = songList[newSongPos]
                playSong(context, songList, songQueue, nextSong, musicServiceCallback)
                return nextSong
            }
        } else {
            val songQ: Song = songQueue.pollFirst()!!
            playSong(context, songList, songQueue, songQ, musicServiceCallback)
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
        if (mediaPlayer?.isPlaying == true) {
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
