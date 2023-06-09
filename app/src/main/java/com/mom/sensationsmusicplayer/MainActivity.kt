package com.mom.sensationsmusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mom.sensationsmusicplayer.ui.HomeScreen
import com.mom.sensationsmusicplayer.ui.MusicPlayerScreen
import com.mom.sensationsmusicplayer.ui.Screen
import com.mom.sensationsmusicplayer.ui.theme.SensationsMusicPlayerTheme
import com.mom.sensationsmusicplayer.util.MusicViewModelProvider
import com.mom.sensationsmusicplayer.util.SetUpNavGraph

class MainActivity : ComponentActivity() {
    private val musicViewModel = MusicViewModelProvider.getMusicViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensationsMusicPlayerTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController = navController, musicViewModel)
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SensationsMusicPlayerTheme {
        //HomeScreen()
    }
}