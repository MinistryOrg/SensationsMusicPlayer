package com.mom.sensationsmusicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mom.sensationsmusicplayer.data.Song
import com.mom.sensationsmusicplayer.ui.HomeScreen
import com.mom.sensationsmusicplayer.ui.MusicPlayerScreen
import com.mom.sensationsmusicplayer.ui.MusicViewModel
import com.mom.sensationsmusicplayer.ui.Screen
import com.mom.sensationsmusicplayer.ui.theme.SensationsMusicPlayerTheme

class MainActivity : ComponentActivity() {
    private val musicViewModel: MusicViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensationsMusicPlayerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
                    composable(Screen.HomeScreen.route){
                        HomeScreen(navController, musicViewModel)
                    }
                    composable(Screen.MusicPlayerScreen.route) { backStackEntry ->
                        MusicPlayerScreen(musicViewModel = musicViewModel)
                    }

                }
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