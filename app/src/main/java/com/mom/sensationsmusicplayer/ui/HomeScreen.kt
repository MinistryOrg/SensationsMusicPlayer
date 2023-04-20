package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mom.sensationsmusicplayer.ui.theme.PlayPauseBtnClr
import com.mom.sensationsmusicplayer.ui.theme.PlayerBarClr
import com.mom.sensationsmusicplayer.ui.theme.SelectedSongTitle
import com.mom.sensationsmusicplayer.ui.theme.SensationsMusicPlayerTheme
import com.mom.sensationsmusicplayer.ui.theme.TextForArtist
import com.mom.sensationsmusicplayer.ui.theme.TextWhite
import com.mom.sensationsmusicplayer.ui.theme.UnknownSongBackground


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val viewModel: MainViewModel = viewModel()
    val musicViewModel : MusicViewModel = viewModel()
            Scaffold(
                modifier =  Modifier.background(MainBackgroundColor),
                topBar = {
                    Column(modifier = Modifier.background(MainBackgroundColor)){
                        CenterAlignedTopAppBar({ ImgLogo() }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            MainBackgroundColor))
                        TabLayout(viewModel = viewModel, musicViewModel)
                    } },
                bottomBar = {
                    PlayerBar(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .padding(bottom = 20.dp, start = 7.dp, end = 7.dp)
                    )
                },
                content = {  }
            )

}

// Logo at the top bar. We use column to avoid overlaping with tabrow
@Composable
fun ImgLogo(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MainBackgroundColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your image resource
            contentDescription = "Image",
            modifier = Modifier
                .width(1000.dp)
                .height(60.dp) // Set the desired height of the image
        )
    }
}

@Composable
fun PlayerBar(
    modifier: Modifier = Modifier
){
    var isPlaying by remember { mutableStateOf(false) }

    val icon = if (isPlaying) {
        R.drawable.play_arrow_icon
    } else {
        R.drawable.pause_icon

    }
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(PlayerBarClr)
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .height(55.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = PlayerBarClr,
        )
    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ){

                Icon(
                    painter = painterResource(id = R.drawable.skip_next_icon),
                    contentDescription = "Skip Next Song" ,
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterEnd)
                        ,
                )
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Play Button" ,
                    tint = PlayPauseBtnClr,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 40.dp)
                        .clickable(onClick = { isPlaying = !isPlaying }),
                )
                Icon(
                    painter = painterResource(id = R.drawable.skip_previous_icon),
                    contentDescription = "Play Button" ,
                    tint = TextWhite,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 80.dp),
                )
                Box(
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .align(Alignment.CenterStart)
                        .clip(RoundedCornerShape(12.dp))

                ){
                    ImgSong()
                }
                Box(
                    modifier = Modifier
                        .padding(start = 70.dp)
                        .align(Alignment.CenterStart)
                ){
                    Text(
                        text = "Song Title \n Artist",
                        fontSize = 14.sp,
                        modifier = Modifier,
                        color = TextWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
        }


    }
}


@Preview
@Composable
fun PlayerBarPreview(){
    SensationsMusicPlayerTheme {
        PlayerBar()
    }
}


@Composable
fun ImgSong(){
    Box(
        modifier = Modifier
            .background(UnknownSongBackground)
            .clip(RoundedCornerShape(12.dp))
            .width(50.dp)
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.song_icon), // Replace with your image resource
            contentDescription = "Image",
            modifier = Modifier
                .align(Alignment.Center)
                .size(20.dp)
        )
    }
}
