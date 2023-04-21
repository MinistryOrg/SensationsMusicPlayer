package com.mom.sensationsmusicplayer.ui

import MusicViewModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.repository.MusicRepoImpl
import com.mom.sensationsmusicplayer.ui.theme.SelectedSongTitle
import com.mom.sensationsmusicplayer.ui.theme.TextForArtist
import com.mom.sensationsmusicplayer.ui.theme.UnknownSongBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SongScreen(viewModel: MainViewModel, musicViewModel: MusicViewModel) {
    val context = LocalContext.current
    val songsState = remember { mutableStateOf(listOf<Song>()) }

    LaunchedEffect(Unit) {
        val songs = MusicRepoImpl().getSongs(context)
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                items(songsState.value) { song ->
                    // call the bar to add the song
                    SongItem(song = song, context, musicViewModel)
                }
            }
        }
    }
}

@Composable
fun SongItem(
    song: Song,
    context: Context,
    musicViewModel: MusicViewModel
){
    val albumArtBitMap = remember {
        mutableStateOf<ImageBitmap?>(null) // initialize bit map
    }
    //Prospatheia mhpws otan clickareis to tragoudi na ginetai kokkinos o titlos kai otan clickareis se allo tragoudi na epanerxetai sto aspro
    val isSelected  = remember { mutableStateOf(false) }

    // is going to re-run every time the albumCover value changes
    LaunchedEffect(song.albumCover) {
        albumArtBitMap.value = loadAlbumArtBitmap(song.albumCover, context)?.asImageBitmap()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))

        ) {
            Box(
                modifier = Modifier
                    .background(UnknownSongBackground)
                    .width(140.dp)
                    .height(135.dp)
                    .clickable {
                        isSelected.value = !isSelected.value // toggle selection state
                        musicViewModel.playSong(context, song.songUri, song)
                    }
            ) {
                if (albumArtBitMap.value != null){
                    Image(
                        bitmap = albumArtBitMap.value!!, // Replace with your image resource
                        contentDescription = "Image",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.song_icon), // Replace with your image resource
                        contentDescription = "Image",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Text(
            text = song.title,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 10.dp),
            color = if (isSelected.value){
                SelectedSongTitle // set desired color when selected
            } else {
                TextForArtist // set default color
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private suspend fun loadAlbumArtBitmap(uri: String, context: Context): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            // loading the album art bitmap from the album art URI
            val inputStream = context.contentResolver.openInputStream(Uri.parse(uri))
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }
}



