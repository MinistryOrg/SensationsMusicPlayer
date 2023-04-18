package com.mom.sensationsmusicplayer.data
data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val albumCover : String,
    val duration: Long
)