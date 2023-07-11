package com.mom.sensationsmusicplayer.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

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
            ACTION_NEXT -> musicViewModel.nextSong() // trigger the next song action
            ACTION_PREV -> musicViewModel.prevSong() // trigger the prev song action
            ACTION_PAUSE -> {
                if (musicViewModel.isPlaying) {
                    musicViewModel.pauseSong() // trigger the pause
                } else {
                    musicViewModel.playSong() // trigger the play
                }
            }
        }
    }
}
