package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor


@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val viewModel: MainViewModel = viewModel()
    val musicViewModel : MusicViewModel = viewModel()
            Scaffold(
                modifier =  Modifier.background(MainBackgroundColor),
                topBar = {
                    Column(modifier = Modifier.background(MainBackgroundColor)){
                        CenterAlignedTopAppBar({ TopNavBarLogo() }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            MainBackgroundColor))
                        TabLayout(viewModel = viewModel, musicViewModel)
                    } }
                ,
//                bottomBar = {
//                    PlayerBar(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(15.dp))
//                            .background(brush = Brush.verticalGradient(
//                                colors = listOf(Color(0x0), Color(0xA6000000)),
//                                startY = 0f,
//                                endY = Float.POSITIVE_INFINITY
//                            ))
//                            .padding(bottom = 20.dp, start = 7.dp, end = 7.dp)
//                    )
//                },
                content = {  }
            )
}

// Logo at the top bar. We use column to avoid overlaping with tabrow
@Composable
fun TopNavBarLogo(){
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