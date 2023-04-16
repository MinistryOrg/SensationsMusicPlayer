package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import androidx.lifecycle.viewmodel.compose.viewModel

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
        content = { }
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

