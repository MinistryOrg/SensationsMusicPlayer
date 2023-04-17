package com.mom.sensationsmusicplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.repository.SongsRepo
import com.mom.sensationsmusicplayer.repository.SongsRepoImpl
import com.mom.sensationsmusicplayer.ui.theme.TextWhite

@Composable
fun SongScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val songsState = remember { mutableStateOf(listOf<Song>()) }

    LaunchedEffect(Unit) {
        val songs = SongsRepoImpl().getSongs(context)
        songsState.value = songs
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF14243C), Color(0xFF0B1422)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .draggable(
                state = viewModel.dragState.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = { },
                onDragStopped = {
                    viewModel.updateTabIndexBasedOnSwipe()
                }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(songsState.value) { song ->
                    Text(
                        text = song.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }

        }
    }
}
