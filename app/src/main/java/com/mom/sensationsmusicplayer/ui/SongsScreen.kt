/*
* Χρήστος Κόκκαλης, 19390090
* Παναγιώτης Γεωργίου, 19390260
* */
package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.repository.MusicRepoImpl
import com.mom.sensationsmusicplayer.ui.theme.SelectedSongArtist
import com.mom.sensationsmusicplayer.ui.theme.SelectedSongTitle
import com.mom.sensationsmusicplayer.ui.theme.TextForArtist
import com.mom.sensationsmusicplayer.ui.theme.TextWhite
import com.mom.sensationsmusicplayer.util.MusicViewModelProvider
import com.mom.sensationsmusicplayer.util.Utill

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "InvalidColorHexValue")
@Composable
fun SongScreen(navController: NavController) {
    val musicViewModel = MusicViewModelProvider.getMusicViewModel()
    musicViewModel.context = LocalContext.current
    musicViewModel.songList = MusicRepoImpl().getSongs(musicViewModel.context!!)

    if (musicViewModel.song == null) {
        musicViewModel.song = musicViewModel.songList!![0]
        musicViewModel.init()
    }
    val updateSong = musicViewModel.updateSong.collectAsState()
    musicViewModel.song = updateSong.value

    //[START OF SCAFFOLD]
    Scaffold(
        //[START OF BOTTOM BAR (PLAYER BAR)]
        bottomBar = {
            //We Call The PlayerBar Composable from PlayerBar.kt and pass the parameters
            PlayerBar(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0x0), Color(0xA6000000)),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
                    .padding(bottom = 20.dp, start = 7.dp, end = 7.dp),
                navController = navController
            )

        }, //[END OF BOTTOM BAR (PLAYER BAR)]
        content = {
            //[START OF MAIN CONTENT OF THE SCREEN (SONG SCREEN)]
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF14243C), Color(0xFF0B1422)),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
                    .padding(top = 80.dp)
                    .fillMaxSize() // To fill the max size of the screen
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    //[START OF LAZY VERTICAL GRID]
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 7.5.dp,
                            end = 7.5.dp,
                            bottom = 100.dp
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        items(musicViewModel.songList!!) { song ->
                            // call the bar to add the song
                            SongItem(song) // update the selected song
                        }
                    }
                    //[END OF LAZY VERTICAL GRID]
                }
            }
            //[END OF MAIN CONTENT OF THE SCREEN (SONG SCREEN)]
        })
    //[END OF SCAFFOLD]
}

@Composable
fun SongItem(
    song: Song
) {
    val musicViewModel = MusicViewModelProvider.getMusicViewModel()
    val context = musicViewModel.context

    val albumArtBitMap = remember {
        mutableStateOf<ImageBitmap?>(null) // initialize bit map
    }

    val utill = Utill()


    val isSelected = remember { mutableStateOf(false) }
    // is going to re-run every time the albumCover value changes
    LaunchedEffect(song.albumCover) {
        albumArtBitMap.value = utill.loadAlbumArtBitmap(song.albumCover, context!!)?.asImageBitmap()
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
                    .width(140.dp)
                    .height(135.dp)
                    .clickable {
                        isSelected.value = !isSelected.value // toggle selection state
                        musicViewModel.song = song
                        musicViewModel.updateSong.value = song
                        musicViewModel.playSong()
                    }) {
                if (albumArtBitMap.value != null) {
                    Image(
                        bitmap = albumArtBitMap.value!!, // Replace with your image resource
                        contentDescription = "Image",
                        modifier = Modifier.align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.unknown_song), // Replace with your image resource
                        contentDescription = "Image",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                Box(modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.queue_music_icon),
                        contentDescription = "queue_add",
                        tint = SelectedSongTitle,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                musicViewModel.addToQueue(song)
                            },
                    )
                }
            }
        }
        Text(
            text = song.title,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 10.dp),
            color = if (isSelected.value) {
                SelectedSongTitle // set desired color when selected
            } else {
                TextWhite // set default color
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = song.artist,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 10.dp),
            color = if (isSelected.value) {
                SelectedSongArtist // set desired color when selected
            } else {
                TextForArtist // set default color
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


