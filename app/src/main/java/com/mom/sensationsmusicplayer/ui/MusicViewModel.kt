import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.mom.sensationsmusicplayer.MainActivity
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.service.MusicService

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
        return NotificationCompat.Builder(context, "MusicPlayerBar")
            .setContentTitle(song.title)
            .setSmallIcon(R.drawable.song_icon)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onCleared() {
        mediaPlayer?.release()
        super.onCleared()
    }
}
