package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.BackBtnClr
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import com.mom.sensationsmusicplayer.ui.theme.MainPlayPauseBtn
import com.mom.sensationsmusicplayer.ui.theme.TextForArtist
import com.mom.sensationsmusicplayer.ui.theme.TextSong
import com.mom.sensationsmusicplayer.ui.theme.TextWhite

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(musicViewModel: MusicViewModel ){

    Scaffold(
        modifier =  Modifier
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
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
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
        content = { MainBody(musicViewModel) }
    )
}

@Composable
fun MusicPlIcon(){
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
fun MainBody(musicViewModel: MusicViewModel){
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
//        verticalArrangement = Arrangement.Top
    ) {
        AlbumDetails(musicViewModel)
        PlayerButtons(musicViewModel = musicViewModel)
    }
}

@Composable
fun AlbumDetails(
    musicViewModel: MusicViewModel
){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
    ){
        Image(
            painter = painterResource(id = R.drawable.unknown_song), // Replace with your image resource
            contentDescription = musicViewModel.song?.albumCover,
            modifier = Modifier
                .width(1000.dp)
                .height(276.dp)
                .align(Alignment.TopCenter)
        )
    }
    Box(
        modifier = Modifier
            .padding(20.dp)

    ) {
            Text(
                musicViewModel.song!!.title,
                textAlign = TextAlign.Center,
                color = TextSong
            )

        musicViewModel.song?.let { song->
            Text(
                song.artist,
                textAlign = TextAlign.Center,
                color = TextForArtist,
                modifier = Modifier
                    .padding(top=30.dp, start = 25.dp)
            )
        }
    }
}

@Composable
fun ProgSliderWithText(){

}

@Composable
fun PlayerButtons(musicViewModel: MusicViewModel){
    Box(
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 30.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Bottom
        ){
            Column {
                Icon( // stop song
                    painter = painterResource(id = R.drawable.stop_icon),
                    contentDescription = "Stop",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            Column {
                Icon( // skip to previous song
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
                Icon( // play and pause song
                    painter = painterResource(id = R.drawable.play_circle_icon),
                    contentDescription = "play",
                    tint = MainPlayPauseBtn,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            musicViewModel.playSong()
                        }
                )
            }
            Column {
                Icon( // go to next song
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
                Icon( // go to queue no idea
                    painter = painterResource(id = R.drawable.queue_music_icon),
                    contentDescription = "Queue",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                )
            }

        }
    }
}


//@Preview
//@Composable
//fun MusicPrev(){
//    SensationsMusicPlayerTheme {
//        MusicPlayerScreen()
//    }
//}