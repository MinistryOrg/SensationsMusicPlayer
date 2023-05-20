package com.mom.sensationsmusicplayer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.mom.sensationsmusicplayer.data.Song
import java.util.LinkedList
import java.util.concurrent.TimeUnit

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    var nextSongVal : Song ?= null
    private var musicServiceCallback : MusicServiceCallback ?= null
    private var notificationService: NotificationService? = null
    private val songQueue  =LinkedList<Song>()

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer()
    }

    fun playSong(context: Context, songList: List<Song>, song: Song, musicServiceCallback: MusicServiceCallback){
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }

        mediaPlayer?.setDataSource(context, song.songUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            if (songQueue.isEmpty()){
                musicServiceCallback.onSongCompleted(nextSong(context,songList, song, musicServiceCallback))
            }
            else{
                // poll used to retrieve and remove the next song from the song queue
                musicServiceCallback.onSongCompleted(nextSong(context,songList,
                    songQueue.poll()!!, musicServiceCallback))
            }
        }
        //seekTo(60000) testing is in milliseconds 60000 is one minute
    }

    fun nextSong(context: Context, songList: List<Song>, song: Song, musicServiceCallback: MusicServiceCallback): Song {
        val songPos = songList.indexOf(song)
        val newSongPos = songPos + 1
        if (songPos != -1 && newSongPos < songList.size) {
            val nextSong = songList[newSongPos]
            playSong(context, songList, nextSong, musicServiceCallback)
            return nextSong
        }
        return song
    }

    fun prevSong(context: Context, songList: List<Song>, song: Song, musicServiceCallback: MusicServiceCallback): Song {
        val songPos = songList.indexOf(song)
        val prevSongPos = songPos - 1
        if (prevSongPos >= 0) {
            val prevSong = songList[prevSongPos]
            Log.d("prev song ", prevSong.title)
            Log.d("prev song position", prevSongPos.toString())
            playSong(context, songList, prevSong, musicServiceCallback)
            return prevSong
        }
        return song
    }

    fun pauseSong() {
        mediaPlayer?.pause()
    }

    fun stopSong() {
        mediaPlayer?.stop()
    }

    //  to move in track
    fun seekTo(position: Float) {
        mediaPlayer?.seekTo(convertMinToMilli(position)) // position is in milliseconds
    }

    private fun convertMinToMilli(position: Float) : Int{
        val milli = position * 60000
        return milli.toInt()
    }

    private fun convertMilli(duration: Int) : String{
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun getCurrentPosition(): String {
        return convertMilli(mediaPlayer?.currentPosition ?: 0)
    }

    fun getDuration(): String {
        return convertMilli(mediaPlayer?.duration ?: 0)
    }

    fun queue(song: Song){
        songQueue.add(song)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

}