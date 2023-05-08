package com.mom.sensationsmusicplayer.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.ui.theme.PlayPauseBtnClr
import com.mom.sensationsmusicplayer.ui.theme.PlayerBarClr
import com.mom.sensationsmusicplayer.ui.theme.TextForArtist
import com.mom.sensationsmusicplayer.ui.theme.TextWhite
import com.mom.sensationsmusicplayer.utill.Utill

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerBar(
    modifier: Modifier = Modifier,
    musicViewModel: MusicViewModel,
    navController: NavController
) {

    var isPlaying by remember { mutableStateOf(false) }
    var index by remember { mutableStateOf(musicViewModel.songList!!.indexOf(musicViewModel.song)) }
    val updateSong = musicViewModel.updateSong.collectAsState()

    val icon = if (isPlaying) {
        R.drawable.play_arrow_icon
    } else {
        R.drawable.pause_icon
    }
    musicViewModel.song = updateSong.value
    //onSongSelected(musicViewModel.song!!)
    index = musicViewModel.songList!!.indexOf(musicViewModel.song)

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(PlayerBarClr)
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .height(55.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = PlayerBarClr,
        ),

        ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                SongBox(musicViewModel.song!!, musicViewModel.context!!, navController)
            }
            Box(
                modifier = Modifier
                    .padding(start = 70.dp, end = 35.dp)
                    .align(Alignment.CenterStart)
            ) {

                Text(
                    text = musicViewModel.song!!.title,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(end = 90.dp)
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            animationMode = MarqueeAnimationMode.Immediately,
                            delayMillis = 2,
                            spacing = MarqueeSpacing(20.dp),
                            velocity = 20.dp
                        ),
                    color = TextWhite
                )
                Text(
                    text = musicViewModel.song!!.artist,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            animationMode = MarqueeAnimationMode.Immediately,
                            delayMillis = 2,
                            spacing = MarqueeSpacing(20.dp),
                            velocity = 20.dp
                        ),
                    color = TextForArtist,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon( // go to next song
                painter = painterResource(id = R.drawable.skip_next_icon),
                contentDescription = "Next Song",
                tint = TextWhite,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .clickable(
                        onClick = {
                            musicViewModel.nextSong()
                            //onSongSelected(musicViewModel.song!!)
                        }
                    )
            )
            Icon(
                // play and pause
                painter = painterResource(id = icon),
                contentDescription = "Play Button",
                tint = PlayPauseBtnClr,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 40.dp)
                    .clickable(
                        onClick = {
                            isPlaying = !isPlaying
                            if (isPlaying) {     // == to true because is Boolean? and maybe return null
                                musicViewModel.stopSong()
                            } else {
                                musicViewModel.playSong()
                                //onSongSelected(musicViewModel.song!!)
                            }
                        }
                    ),
            )
            Icon( // go to previous song
                painter = painterResource(id = R.drawable.skip_previous_icon),
                contentDescription = "Previous song",
                tint = TextWhite,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 80.dp)
                    .clickable(
                        onClick = {
                            musicViewModel.prevSong()
                           // onSongSelected(musicViewModel.song!!)
                        }
                    )
            )
        }
    }
}

@Composable
fun SongBox(
    song: Song,
    context: Context,
    navController: NavController
) {
    val utill = Utill()
    val albumArtBitMap = remember {
        mutableStateOf<ImageBitmap?>(null) // initialize bit map
    }
    // is going to re-run every time the albumCover value changes
    LaunchedEffect(song.albumCover) {
        albumArtBitMap.value = utill.loadAlbumArtBitmap(song.albumCover, context)?.asImageBitmap()
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp)),
    ) {

        if (albumArtBitMap.value != null){
            Image(
                bitmap = albumArtBitMap.value!!, // Replace with your image resource
                contentDescription = "Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        //open the new screen
                        navController.navigate(Screen.MusicPlayerScreen.route)
                    }
            )

        } else {
            Image(
                painter = painterResource(id = R.drawable.unknown_song), // Replace with your image resource
                contentDescription = "Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        navController.navigate(Screen.MusicPlayerScreen.route)
                    }
            )
    }
    }
}

