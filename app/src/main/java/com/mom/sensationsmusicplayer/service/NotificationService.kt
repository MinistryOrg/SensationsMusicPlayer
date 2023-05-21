package com.mom.sensationsmusicplayer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.mom.sensationsmusicplayer.MainActivity
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.utill.MusicPlayerNotificationReceiver
import com.mom.sensationsmusicplayer.utill.MusicViewModelProvider

class NotificationService : Service() {
    fun playingInTheBackground(context: Context) {
        val notification = createNotification(context)
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(1, notification)
    }

    private fun createNotification(
        context: Context,
    ): Notification {
        val musicViewModel = MusicViewModelProvider.getMusicViewModel()

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val mediaSession = MediaSessionCompat(context, "tag")

        Log.d("M 1", musicViewModel.updateSong.value.title)
        Log.d("M 2", musicViewModel.song!!.title)

        val song = musicViewModel.updateSong.value
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
        playbackStateBuilder.setState(
            PlaybackStateCompat.STATE_PLAYING,
            0,
            1.0f,
            SystemClock.elapsedRealtime()
        )
        mediaSession.setPlaybackState(playbackStateBuilder.build())

        //  must have the same names.
        val channelId = "MusicPlayerBar"
        val channelName = "MusicPlayerBar"

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(channelId, channelName, importance)
        ContextCompat.getSystemService(context, NotificationManager::class.java)
            ?.createNotificationChannel(
                channel
            )

        val pauseAction = NotificationCompat.Action(
            R.drawable.pause_icon,
            "Pause",
            PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, MusicPlayerNotificationReceiver::class.java)
                    .setAction(MusicPlayerNotificationReceiver.ACTION_NEXT),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val nextAction = NotificationCompat.Action(
            R.drawable.skip_next_icon,
            "Next",
            PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, MusicPlayerNotificationReceiver::class.java)
                    .setAction(MusicPlayerNotificationReceiver.ACTION_NEXT),
                PendingIntent.FLAG_UPDATE_CURRENT

            )
        )

        val prevAction = NotificationCompat.Action(
            R.drawable.skip_previous_icon,
            "Previous",
            PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, MusicPlayerNotificationReceiver::class.java)
                    .setAction(MusicPlayerNotificationReceiver.ACTION_PREV),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        // returns the notification ui


        val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
            .setShowActionsInCompactView(0, 1, 2)
            .setMediaSession(mediaSession.sessionToken)

        return NotificationCompat.Builder(context, "MusicPlayerBar")
            .setSmallIcon(R.drawable.notif_icon)
            .setContentTitle(song.title)
            .setContentText(song.artist)
            .setContentIntent(pendingIntent)
            .setStyle(mediaStyle)
            .addAction(prevAction)
            .addAction(pauseAction)
            .addAction(nextAction)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCategory(NotificationCompat.CATEGORY_TRANSPORT) // Set category as transport
            .build()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}