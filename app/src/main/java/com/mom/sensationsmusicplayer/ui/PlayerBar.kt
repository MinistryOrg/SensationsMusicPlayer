package com.mom.sensationsmusicplayer.ui

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.ui.theme.PlayPauseBtnClr
import com.mom.sensationsmusicplayer.ui.theme.PlayerBarClr
import com.mom.sensationsmusicplayer.ui.theme.SensationsMusicPlayerTheme
import com.mom.sensationsmusicplayer.ui.theme.TextForArtist
import com.mom.sensationsmusicplayer.ui.theme.TextWhite

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerBar(
    modifier: Modifier = Modifier,
    musicViewModel: MusicViewModel,
    context: Context,
    songsList: List<Song>,
    song: Song,
    onSongSelected: (Song) -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }
    var index by remember { mutableStateOf(songsList.indexOf(song)) }

    val icon = if (isPlaying) {
        R.drawable.play_arrow_icon
    } else {
        R.drawable.pause_icon
    }

    onSongSelected(song)
    index = songsList.indexOf(song)

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
            Icon( // go to next song
                painter = painterResource(id = R.drawable.skip_next_icon),
                contentDescription = "Next Song",
                tint = TextWhite,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .clickable(
                        onClick = {
                            musicViewModel.nextSong(context = context, songsList, songsList[index])
                            if (index != -1 && index + 1 < songsList.size) {
                                index += 1
                            }
                            onSongSelected(songsList[index])
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
                            if (isPlaying) {        // the user want to pause
                                musicViewModel.stopSong()
                            } else {
                                onSongSelected(songsList[index])
                                musicViewModel.playSong(context, songsList[index])
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
                            musicViewModel.prevSong(context, songsList, songsList[index])
                            if (index - 1 > 0) {
                                index -= 1
                            }
                            onSongSelected(songsList[index])
                        }
                    )
            )
            Box(
                modifier = Modifier
                    .padding(start = 3.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                SongBox()
            }
            Box(
                modifier = Modifier
                    .padding(start = 70.dp, end = 35.dp)
                    .align(Alignment.CenterStart)
            ) {

                Log.d("Song  title ", songsList[index].title)
                Text(
                    text = songsList[index].title,
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
                    text = songsList[index].artist,
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
        }
    }
}

@Composable
fun SongBox() {
    Box(
        //TODO EDW THA MPEI WS PARAMETROS TO ALBUM COVER H KAI OXI IDK
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
    ) {
        //TODO EDW THA GINEI ALLAGH TOU ALBUM COVER
//        if (albumArtBitMap.value != null){
//            Image(
//                bitmap = albumArtBitMap.value!!, // Replace with your image resource
//                contentDescription = "Image",
//                modifier = Modifier
//                    .align(Alignment.Center),
//                contentScale = ContentScale.Crop
//            )
//        } else {
        Image(
            painter = painterResource(id = R.drawable.unknown_song), // Replace with your image resource
            contentDescription = "Image",
            modifier = Modifier
                .align(Alignment.Center)
        )
//    }
    }
}

@Preview
@Composable
fun SongBoxPreview() {
    SensationsMusicPlayerTheme {
        SongBox()
    }
}