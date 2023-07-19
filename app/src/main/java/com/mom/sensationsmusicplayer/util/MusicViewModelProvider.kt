/*
* Χρήστος Κόκκαλης, 19390090
* Παναγιώτης Γεωργίου, 19390260
* */
package com.mom.sensationsmusicplayer.util

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