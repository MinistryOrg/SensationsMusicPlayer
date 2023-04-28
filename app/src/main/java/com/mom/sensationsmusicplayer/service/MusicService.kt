package com.mom.sensationsmusicplayer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import com.mom.sensationsmusicplayer.MainActivity
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.utill.MusicPlayerNotificationReceiver

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

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

        playingInTheBackground(context, song)
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

    fun moveInTruck(context: Context, songList: List<Song>, position: Int){

    }

    private fun playingInTheBackground(context: Context, song: Song) {
        val intent = Intent(context, MusicService::class.java)
        intent.putExtra("songUri", song.songUri.toString())

        context.startService(intent)

        val notification = createNotification(context, song)
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(1, notification)
    }

    private fun createNotification(context: Context, song: Song): Notification {
        val musicPlayerNotificationReceiver = MusicPlayerNotificationReceiver()
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val mediaSession = MediaSessionCompat(context, "tag")

        // Set the metadata for the media session
        val metadataBuilder = MediaMetadataCompat.Builder()
        metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.title)
        metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.artist)
        metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, song.album)
        mediaSession.setMetadata(metadataBuilder.build())

        // Set the playback state for the media session
        val playbackStateBuilder = PlaybackStateCompat.Builder()
        playbackStateBuilder.setActions(
            PlaybackStateCompat.ACTION_PLAY or
                    PlaybackStateCompat.ACTION_PAUSE or
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
        )
        playbackStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f, SystemClock.elapsedRealtime())
        mediaSession.setPlaybackState(playbackStateBuilder.build())

        //  must have the same names.
        val channelId = "MusicPlayerBar"
        val channelName = "MusicPlayerBar"

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(channelId, channelName, importance)
        ContextCompat.getSystemService(context, NotificationManager::class.java)?.createNotificationChannel(
            channel
        )

        val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
            .setShowActionsInCompactView(0, 1, 2)
            .setMediaSession(mediaSession.sessionToken)

        // TODO kapos etsi kaneis ta koumpia alla den exo idea pws mporo na valo functionality
        val pauseAction = NotificationCompat.Action(
            R.drawable.pause_icon,
            "Pause",
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_PAUSE
            )
        )

        val nextAction = NotificationCompat.Action(
            R.drawable.skip_next_icon,
            "Next",
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            )
        )

        val prevAction = NotificationCompat.Action(
            R.drawable.skip_previous_icon,
            "Previous",
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            )
        )

        val notificationUI = NotificationCompat.Builder(context, "MusicPlayerBar")
            .setSmallIcon(R.drawable.notif_icon)
            .setContentTitle(song.title)
            .setContentText(song.artist)
            .setContentIntent(pendingIntent)
            .setStyle(mediaStyle)
            .addAction(prevAction)
            .addAction(pauseAction)
            .addAction(nextAction)
            .build()

        return notificationUI
    }


    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

}