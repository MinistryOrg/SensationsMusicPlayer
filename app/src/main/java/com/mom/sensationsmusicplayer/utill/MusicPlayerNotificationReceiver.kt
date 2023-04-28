package com.mom.sensationsmusicplayer.utill

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlin.math.log

class MusicPlayerNotificationReceiver : BroadcastReceiver(){
    override fun onReceive(context : Context?, intent : Intent?) {
        Log.d("CALL IT", "PREVIOUS")
        if (intent?.action == "previous") {
            Log.d("PREVIOUS", "PREVIOUS")
            // Handle back button click
        } else if (intent?.action == "play") {
            Log.d("PLAY", "PLAYING")
            // Handle pause/play button click
        } else if (intent?.action == "next") {
            Log.d("NEXT","NEXT")
            // Handle next button click
        }
    }
}