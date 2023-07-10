package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.BackBtnClr
import com.mom.sensationsmusicplayer.ui.theme.InActiveTrackColor
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import com.mom.sensationsmusicplayer.ui.theme.MainPlayPauseBtn
import com.mom.sensationsmusicplayer.ui.theme.SliderColor
import com.mom.sensationsmusicplayer.ui.theme.TextForArtist
import com.mom.sensationsmusicplayer.ui.theme.TextSong
import com.mom.sensationsmusicplayer.ui.theme.TextWhite
import com.mom.sensationsmusicplayer.util.MusicViewModelProvider
import com.mom.sensationsmusicplayer.util.Utill
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .background(MainBackgroundColor)//To Change the background color
        ,
        topBar = {
            Column(
                modifier = Modifier
                    .background(MainBackgroundColor)
            ) {
                //Center the image logo
                CenterAlignedTopAppBar(
                    { MusicPlIcon() },
                    //Change default background color
                    colors = TopAppBarDefaults
                        .centerAlignedTopAppBarColors(MainBackgroundColor),
                    //[NAVIGATION BUTTON TO GO BACK]
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.back_icon),
                                contentDescription = "Back",
                                tint = BackBtnClr,
                                modifier = Modifier
                                    .size(100.dp),
                            )
                        }
                    }
                )
            }
        },
        content = { MainBody(navController) }
    )
}

@Composable
fun MusicPlIcon() {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxHeight()
            .background(MainBackgroundColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.notif_icon), // Replace with your image resource
            contentDescription = "Image",
            modifier = Modifier
                .width(100.dp)
                .height(50.dp), // Set the desired height of the image
            alignment = Alignment.Center
        )
    }
}

@Composable
fun MainBody(navController: NavController) {
    var isPlaying by remember { mutableStateOf(false) }
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
            .padding(top = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AlbumDetails(navController)
    }
}

@Composable
fun AlbumDetails(navController: NavController) {
    val musicViewModel = MusicViewModelProvider.getMusicViewModel()
    val updateSong = musicViewModel.updateSong.collectAsState()
    musicViewModel.song = updateSong.value

    val albumArtBitMap = remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    // When Play or Pause Button is pressed then it changes
    var isPlaying by remember { mutableStateOf(false) }
    var stopClicked by remember { mutableStateOf(false) }


    val utill = Utill()

    LaunchedEffect(musicViewModel.song!!.albumCover, musicViewModel.isPlaying) {
        albumArtBitMap.value =
            utill.loadAlbumArtBitmap(musicViewModel.song!!.albumCover, musicViewModel.context!!)
                ?.asImageBitmap()
        Log.d("Is playing", musicViewModel.isPlaying.toString())
        //isPlaying = musicViewModel.isPlaying
    }

    val icon = if (isPlaying) {
        R.drawable.play_circle_icon
    } else {
        R.drawable.pause_circle_icon
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
    ) {
        if (albumArtBitMap.value != null) {
            Image(
                bitmap = albumArtBitMap.value!!,
                contentDescription = "Album Cover Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(250.dp)
                    .width(250.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.unknown_song),
                contentDescription = "No Album Cover Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(250.dp)
                    .width(250.dp)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            musicViewModel.song!!.title,
            color = TextSong
        )
        Text(
            musicViewModel.song!!.artist,
            color = TextForArtist,
            modifier = Modifier.padding(top = 50.dp)
        )
    }

    ProgSliderWithText(musicViewModel)

    Box(
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 30.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.stop_icon),
                    contentDescription = "Stop",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            musicViewModel.stopSong()
                            isPlaying = !isPlaying
                            stopClicked = !stopClicked
                        }
                )
            }
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.skip_previous_icon),
                    contentDescription = "Previous",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            musicViewModel.prevSong()
                        }
                )
            }
            Column {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "play & pause",
                    tint = MainPlayPauseBtn,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable(
                            onClick = {
                                isPlaying = !isPlaying
                                if (isPlaying) {
                                    if (stopClicked) {
                                        musicViewModel.stopSong()
                                        stopClicked = !stopClicked
                                    } else {
                                        musicViewModel.pauseSong()
                                    }
                                } else {
                                    musicViewModel.playSong()
                                }
                            }
                        )
                )
            }
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.skip_next_icon),
                    contentDescription = "Next Song",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            musicViewModel.nextSong()
                        }
                )
            }
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.queue_music_icon),
                    contentDescription = "Queue",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            navController.navigate(Screen.QueueScreen.route)
                        }
                )
            }
        }
    }
}

@Composable
fun ProgSliderWithText(
    musicViewModel: MusicViewModel,
) {
    val songsList = musicViewModel.songList

    var index by remember { mutableStateOf(songsList!!.indexOf(musicViewModel.song)) }
    index = songsList!!.indexOf(musicViewModel.song)
    musicViewModel.song = songsList[index]

    val sliderValue = remember {
        mutableStateOf(0f)
    }

    val test = remember {
        mutableStateOf("00:00")
    }

    val stopPlaying = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(test.value, stopPlaying.value) {
        while (true) {
            test.value = musicViewModel.getCurrentPosition("time")
            sliderValue.value = musicViewModel.getCurrentPosition("slider").toFloat()
            stopPlaying.value = musicViewModel.stopPlaying
            delay(1000)
        }
    }

    if (musicViewModel.stopPlaying) {
        sliderValue.value = 0f
        test.value = "00:00"
    }

    Slider(
        value = sliderValue.value,
        modifier = Modifier.padding(horizontal = 20.dp),
        onValueChange = { sliderValue_ ->
            sliderValue.value = sliderValue_
        },
        onValueChangeFinished = {
            musicViewModel.moveInTrack(sliderValue.value)
        },
        valueRange = 0f..musicViewModel.getDuration(),
        colors = SliderDefaults.colors(
            thumbColor = SliderColor,
            activeTrackColor = SliderColor,
            inactiveTrackColor = InActiveTrackColor
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                text = test.value,
                color = TextWhite,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            Text(
                musicViewModel.getFormat(),
                textAlign = TextAlign.End,
                color = TextSong
            )
        }
    }
}