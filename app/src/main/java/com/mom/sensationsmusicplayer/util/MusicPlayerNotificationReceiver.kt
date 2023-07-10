package com.mom.sensationsmusicplayer.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MusicPlayerNotificationReceiver : BroadcastReceiver() {
    private val musicViewModel = MusicViewModelProvider.getMusicViewModel()

    companion object {
        const val ACTION_NEXT = "com.mom.sensationsmusicplayer.ACTION_NEXT"
        const val ACTION_PREV = "com.mom.sensationsmusicplayer.ACTION_PREV"
        const val ACTION_PAUSE = "com.mom.sensationsmusicplayer.ACTION_PAUSE"
        const val ACTION_PLAY = "com.mom.sensationsmusicplayer.ACTION_PLAY"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_NEXT -> musicViewModel.nextSong()
            ACTION_PREV -> musicViewModel.prevSong()
            ACTION_PAUSE -> {
                if (musicViewModel.isPlaying) {
                    Log.d("Where 1", "1")
                    musicViewModel.pauseSong()
                } else {
                    Log.d("Where 1", "2")
                    musicViewModel.playSong()
                }
            }
        }
    }
}
