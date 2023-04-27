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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SensationsMusicPlayerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
                    composable(Screen.HomeScreen.route){
                        HomeScreen(navController)
                    }
                    composable(Screen.MusicPlayerScreen.route){
                        MusicPlayerScreen()
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