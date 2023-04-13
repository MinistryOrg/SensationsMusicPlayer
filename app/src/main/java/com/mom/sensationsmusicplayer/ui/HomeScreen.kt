package com.mom.sensationsmusicplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mom.sensationsmusicplayer.R


@Composable
fun HomeScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Background()
    }
}

@Composable
fun Background(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF14243C), Color(0xFF0B1422)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your image resource
                contentDescription = "Image",
                modifier = Modifier
                    .width(1000.dp)
                    .height(60.dp) // Set the desired height of the image
                    .align(Alignment.TopCenter) // Align the image to the top center of the box
            )
        }

    }
}