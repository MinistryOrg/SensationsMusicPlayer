package com.mom.sensationsmusicplayer.ui

sealed class Screen (val route : String) {
    object HomeScreen : Screen("home_screen")
    object MusicPlayerScreen : Screen ("music_player_screen")
}
