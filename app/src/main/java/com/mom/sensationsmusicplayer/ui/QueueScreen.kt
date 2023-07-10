package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.BackBtnClr
import com.mom.sensationsmusicplayer.ui.theme.DividerClr
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import com.mom.sensationsmusicplayer.ui.theme.TextSong

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueueScreen( navController: NavController, musicViewModel: MusicViewModel) {
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
        content = {
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
                    .padding(top = 75.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "Queue",
                        color = TextSong,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    color = DividerClr,
                    thickness = 2.dp
                )
                //Lazy column to add songs to queue
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    content = {

                        // Add 5 items
                        items(musicViewModel.songQueue) { song ->
                            Text(
                                text = song.title,
                                color = TextSong,
                                fontSize = 17.sp
                            )
                            Divider(
                                modifier = Modifier
                                    .padding(horizontal = 1.dp, vertical = 5.dp),
                                color = DividerClr,
                                thickness = 1.dp
                            )
                        }

                    })
            }
        }
    )
}