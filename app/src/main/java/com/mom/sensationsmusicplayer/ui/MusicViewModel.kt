import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.widget.GridView
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.MainActivity
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor

class MusicViewModel : ViewModel() {

    private var mediaPlayer: MediaPlayer? = null

    fun playSong(context: Context, songUri: Uri, song: Song) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        } else {
            mediaPlayer?.reset()
        }

        mediaPlayer?.setDataSource(context, songUri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
        playingInTheBackground(context, songUri, song)
    }

    fun stopSong() {
        mediaPlayer?.stop()
    }

    fun playingInTheBackground(context: Context, songUri: Uri, song: Song) {
        val intent = Intent(context, MusicService::class.java)
        intent.putExtra("songUri", songUri.toString())

        context.startService(intent)

        val notification = createNotification(context, songUri, song)
        val notificationManager = getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(1, notification)
    }

    // TODO ADD A BETTER DESIGN HERE :)

    private fun createNotification(context: Context, songUri: Uri, song: Song): Notification {
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
        getSystemService(context, NotificationManager::class.java)?.createNotificationChannel(channel)


        // TODO ΕΔΩ ΦΤΙΑΧΝΕΙΣ ΤΟ DESIGN ΔΕΝ ΞΕΡΩ ΑΝ ΜΠΟΡΕΙΣ ΝΑ ΚΑΛΕΣΕΙΣ COMPOSABLE Η ΑΝ ΕΧΕΙ ΚΑΤΙ DEFAULT
//        val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
//            action = ACTION_SNOOZE
//            putExtra(EXTRA_NOTIFICATION_ID, 0)
//        }
//        val snoozePendingIntent: PendingIntent =
//            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)


        val notificationUI = NotificationCompat.Builder(context, "MusicPlayerBar")
             .setSmallIcon(R.drawable.notif_icon)
             .setContentTitle(song.title)
             .setContentText(song.artist)
            .setContentIntent(pendingIntent)
//             .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
//                 snoozePendingIntent)
             .build()


        return notificationUI
    }



    override fun onCleared() {
        mediaPlayer?.release()
        super.onCleared()
    }
}

