package com.mom.sensationsmusicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mom.sensationsmusicplayer.R
import com.mom.sensationsmusicplayer.ui.theme.DividerClr
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import com.mom.sensationsmusicplayer.ui.theme.SelectedTabOption


@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavController){
    val viewModel: MainViewModel = viewModel()
    val musicViewModel : MusicViewModel = viewModel()
    //[START-TOP BAR]
            Scaffold(
                modifier =  Modifier.background(MainBackgroundColor), //To Change the background color
                topBar = {
                    Column(modifier = Modifier.background(MainBackgroundColor)){
                        //Center the image logo
                        CenterAlignedTopAppBar(
                            { TopNavBarLogo() },
                            //Change default background color
                            colors = TopAppBarDefaults
                                .centerAlignedTopAppBarColors(MainBackgroundColor)
                        )
                        //We call the tab layout
                        TitleOfTopBar()
                        SongScreen(viewModel = viewModel, musicViewModel,navController)
                    } }
                ,
                content = {


                }
            )
    //[END-TOP BAR]
}

// [START COMPOSABLE IMAGE-LOGO]
@Composable
fun TopNavBarLogo(){
    Column( //we use column to avoid operlaping the top bar with the image
        modifier = Modifier
            .fillMaxWidth()
            .background(MainBackgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your image resource
            contentDescription = "Image",
            modifier = Modifier
                .width(500.dp)
                .height(60.dp) // Set the desired height of the image
        )
    }
}
// [END COMPOSABLE IMAGE-LOGO]

@Composable
fun TitleOfTopBar(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Songs",
            fontSize = 18.sp,
            color = SelectedTabOption,
            fontWeight = FontWeight.Bold
        )
    }
    Column() {
        Divider(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp),
            color = DividerClr,
            thickness = 2.dp
        )
    }
}