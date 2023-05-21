package com.mom.sensationsmusicplayer.utill

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MusicPlayerNotificationReceiver : BroadcastReceiver() {
    private val musicViewModel = MusicViewModelProvider.getMusicViewModel()
    companion object {
        const val ACTION_NEXT = "com.mom.sensationsmusicplayer.ACTION_NEXT"
        const val ACTION_PREV = "com.mom.sensationsmusicplayer.ACTION_PREV"
        const val ACTION_PAUSE =  "com.mom.sensationsmusicplayer.ACTION_PAUSE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            ACTION_NEXT -> musicViewModel.nextSong()
            ACTION_PREV -> musicViewModel.prevSong()
            ACTION_PAUSE -> musicViewModel.pauseSong()
        }
    }
}
