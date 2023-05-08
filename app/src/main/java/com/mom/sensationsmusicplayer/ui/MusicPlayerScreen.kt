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
import com.mom.sensationsmusicplayer.utill.Utill

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(musicViewModel: MusicViewModel, navController: NavController ){
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
                    //[NAVIGATION BUTTON TO GO BACK]
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
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
    ) {
        AlbumDetails(musicViewModel)
    }
}

@Composable
fun AlbumDetails(
    musicViewModel: MusicViewModel,
){

    val updateSong = musicViewModel.updateSong.collectAsState()
    musicViewModel.song = updateSong.value

    val albumArtBitMap = remember {
        mutableStateOf<ImageBitmap?>(null) // initialize bit map
    }

    // When Play or Pause Button is pressed then it changes
    var isPlaying by remember{ mutableStateOf(false)}
    val icon = if(isPlaying){
        R.drawable.play_circle_icon
    }else{
        R.drawable.pause_circle_icon
    }

    val utill  = Utill()

    // is going to re-run every time the albumCover value changes
    LaunchedEffect(musicViewModel.song!!.albumCover) {
        albumArtBitMap.value = utill.loadAlbumArtBitmap(musicViewModel.song!!.albumCover, musicViewModel.context!!)?.asImageBitmap()
    }

    //[Box that contains the album cover of the song. If the Album cover exists then it shows normanly. In other case it shows the unknown album cover image]
    Box(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
    ){
        if (albumArtBitMap.value != null) {
            Image(
                bitmap = albumArtBitMap.value!!, // Replace with your image resource
                contentDescription = "Album Cover Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(250.dp)
                    .width(250.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.unknown_song), // Replace with your image resource
                contentDescription = "No Album Cover Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(250.dp)
                    .width(250.dp)
            )
        }
    }
    //[Box that contains the Title of the song and the artist]
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
                modifier = Modifier.padding(top=50.dp)
            )
    }

    //[Composable Function tha contains the slider]
    ProgSliderWithText( musicViewModel )

    // [Player buttons]
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
            //[STOP SONG BUTTON]
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.stop_icon),
                    contentDescription = "Stop",
                    tint = TextWhite,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { musicViewModel.stopSong() }
                )
            }
            //[SKIP TO PREV SONG BUTTON]
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
            //[PLAY AND PAUSE SONG BUTTON]
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
                                if (isPlaying) {     // == to true because is Boolean? and maybe return null
                                    musicViewModel.stopSong()
                                } else {
                                    musicViewModel.playSong()
                                }
                            }
                        )
                )
            }
            //[SKIP TO NEXT SONG BUTTON]
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
            //[QUEUE SONG BUTTON]
            Column {
                Icon(
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

@Composable
fun ProgSliderWithText(
    musicViewModel: MusicViewModel
){
    val songsList = musicViewModel.songList

    var index by remember { mutableStateOf(songsList!!.indexOf(musicViewModel.song)) }
    index = songsList!!.indexOf(musicViewModel.song)
    musicViewModel.song = songsList[index]

    var sliderValue by remember {
        mutableStateOf(0f) // pass the initial value
    }

    Slider(
        value = sliderValue,
        modifier = Modifier.padding(horizontal = 20.dp)
        ,onValueChange = { sliderValue_ ->
            sliderValue = sliderValue_
        },
        onValueChangeFinished = {
            musicViewModel.moveInTrack(sliderValue)
        },
        valueRange = 0f..10f,
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
    ){
        Text(
            text = sliderValue.toString(),
            color = TextWhite,
            textAlign = TextAlign.Start
        )
//        Text(
//            songsList[index].duration.toString(),
//            textAlign = TextAlign.End,
//            color = TextSong
//        )
    }
}
