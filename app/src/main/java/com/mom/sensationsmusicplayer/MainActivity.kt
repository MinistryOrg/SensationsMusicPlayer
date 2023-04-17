package com.mom.sensationsmusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mom.sensationsmusicplayer.ui.HomeScreen
import com.mom.sensationsmusicplayer.ui.theme.SensationsMusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensationsMusicPlayerTheme {
                HomeScreen()

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SensationsMusicPlayerTheme {
        HomeScreen()
    }
}