package com.mom.sensationsmusicplayer.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.MainActivity
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService

class MusicViewModel : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null

    fun playSong(context: Context, song: Song) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }
        mediaPlayer?.setDataSource(context, song.songUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()

        playingInTheBackground(context, song)
    }

    fun nextSong(context: Context, songList: List<Song>, song: Song) {
        val songPos = songList.indexOf(song)
        val newSongPos = songPos + 1
        if (songPos != -1 && newSongPos < songList.size) {
            val nextSong = songList[newSongPos]
            playSong(context, nextSong)
        }
    }

    fun prevSong(context: Context, songList: List<Song>, song: Song) {
        val songPos = songList.indexOf(song)
        val nextSongPos = songPos - 1
        if (nextSongPos > 0) {
            val prevSong = songList[nextSongPos]
            playSong(context, prevSong)
        }
    }

    fun stopSong() {
        mediaPlayer?.stop()
    }

    fun pauseSong(){
        mediaPlayer?.pause()
    }

    fun playingInTheBackground(context: Context, song: Song) {
        val intent = Intent(context, MusicService::class.java)
        intent.putExtra("songUri", song.songUri.toString())

        context.startService(intent)

        val notification = createNotification(context, song)
        val notificationManager = getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(1, notification)
    }

    // TODO ADD A BETTER DESIGN HERE :)

    private fun createNotification(context: Context, song: Song): Notification {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        //  must have the same names
        val channelId = "MusicPlayerBar"
        val channelName = "MusicPlayerBar"

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        getSystemService(context, NotificationManager::class.java)?.createNotificationChannel(
            channel
        )


        val notificationUI = NotificationCompat.Builder(context, "MusicPlayerBar")
            .setSmallIcon(R.drawable.notif_icon)
            .setContentTitle(song.title)
            .setContentText(song.artist)
            .setContentIntent(pendingIntent)
            .build()


        return notificationUI
    }

    override fun onCleared() {
        mediaPlayer?.release()
        super.onCleared()
    }
}

