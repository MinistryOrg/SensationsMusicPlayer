/*
* Χρήστος Κόκκαλης, 19390090
* Παναγιώτης Γεωργίου, 19390260
* */
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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.mom.sensationsmusicplayer.MainActivity
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.util.MusicPlayerNotificationReceiver
import com.mom.sensationsmusicplayer.util.MusicViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NotificationService : Service() {
    fun playingInTheBackground(context: Context) {
        val musicViewModel = MusicViewModelProvider.getMusicViewModel()
        // get the updateSong from the music view mode
        musicViewModel.updateSong.onEach {   // to update song every time the updateSong in ViewModel is changed
            val notification = createNotification(context)
            val notificationManager =
                ContextCompat.getSystemService(context, NotificationManager::class.java)
            notificationManager?.notify(1, notification)

        }.launchIn(
            CoroutineScope(Dispatchers.Default)
        )

    }

    private fun createNotification(
        context: Context,
    ): Notification {
        val musicViewModel = MusicViewModelProvider.getMusicViewModel()
        // Create a notification intent to open the MainActivity
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        // Create a media session for controlling playback
        val mediaSession = MediaSessionCompat(context, "tag")
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
        // Create a notification channel
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(channelId, channelName, importance)
        ContextCompat.getSystemService(context, NotificationManager::class.java)
            ?.createNotificationChannel(
                channel
            )

        val isPlaying = musicViewModel.isPlaying//  get the playback state

        val playAction = NotificationCompat.Action(
            R.drawable.play_arrow_icon,
            "Play",
            PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, MusicPlayerNotificationReceiver::class.java)
                    .setAction(MusicPlayerNotificationReceiver.ACTION_PLAY),
                PendingIntent.FLAG_IMMUTABLE
            )
        )

        val pauseAction = NotificationCompat.Action(
            R.drawable.pause_icon,
            "Pause",
            PendingIntent.getBroadcast(
                context,
                0,
                Intent(context, MusicPlayerNotificationReceiver::class.java)
                    .setAction(MusicPlayerNotificationReceiver.ACTION_PAUSE),
                PendingIntent.FLAG_IMMUTABLE
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
                PendingIntent.FLAG_IMMUTABLE

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
                PendingIntent.FLAG_IMMUTABLE
            )
        )
        //we tried to change the button when pressed. Not working
        val playPauseAction = if (isPlaying) {
            pauseAction
        } else {
            playAction
        }
        // Create a media style notification with actions
        val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
            .setShowActionsInCompactView(0, 1, 2)
            .setMediaSession(mediaSession.sessionToken)
        // returns the notification ui
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