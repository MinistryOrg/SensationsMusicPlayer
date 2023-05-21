package com.mom.sensationsmusicplayer.utill

import com.mom.sensationsmusicplayer.ui.MusicViewModel

object MusicViewModelProvider {
    private var musicViewModel: MusicViewModel? = null

    fun getMusicViewModel(): MusicViewModel {
        if (musicViewModel == null) {
            musicViewModel = MusicViewModel()
        }
        return musicViewModel!!
    }
}