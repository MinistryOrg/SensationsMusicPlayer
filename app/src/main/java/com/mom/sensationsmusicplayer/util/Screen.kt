package com.mom.sensationsmusicplayer.util

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object MusicPlayerScreen : Screen("music_player_screen")
    object QueueScreen : Screen("queue_screen")
}
