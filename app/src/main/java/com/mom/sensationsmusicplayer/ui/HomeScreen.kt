package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mom.sensationsmusicplayer.ui.theme.PlayerBarClr
import com.mom.sensationsmusicplayer.ui.theme.TextWhite
import com.mom.sensationsmusicplayer.ui.theme.UnknownSongBackground


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val viewModel: MainViewModel = viewModel()
            Scaffold(
                modifier =  Modifier.background(MainBackgroundColor),
                topBar = {
                    Column(modifier = Modifier.background(MainBackgroundColor)){
                        CenterAlignedTopAppBar({ ImgLogo() }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            MainBackgroundColor))
                        TabLayout(viewModel = viewModel)
                    } },
                bottomBar = {
                            PlayerBar(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .padding(bottom = 20.dp, start = 10.dp, end = 7.dp)
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
fun PlayerBar( modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .background(PlayerBarClr)
            .clip(RoundedCornerShape(15.dp))
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3)

        ){

        }
    }
}

@Composable
fun ImgSong(){
    BoxWithConstraints(
        modifier = Modifier
            .background(UnknownSongBackground)
            .clip(RoundedCornerShape(12.dp))
            .width(140.dp)
            .height(135.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.song_icon), // Replace with your image resource
            contentDescription = "Image",
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}
