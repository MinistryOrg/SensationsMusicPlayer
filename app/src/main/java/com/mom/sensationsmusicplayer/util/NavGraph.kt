/*
* Χρήστος Κόκκαλης, 19390090
* Παναγιώτης Γεωργίου, 19390260
* */
package com.mom.sensationsmusicplayer.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mom.sensationsmusicplayer.ui.HomeScreen
import com.mom.sensationsmusicplayer.ui.MusicPlayerScreen
import com.mom.sensationsmusicplayer.ui.MusicViewModel
import com.mom.sensationsmusicplayer.ui.QueueScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    musicViewModel: MusicViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController)
        }
        composable(
            Screen.MusicPlayerScreen.route
        ) {
            MusicPlayerScreen(navController)
        }
        composable(
            Screen.QueueScreen.route
        ) {
            QueueScreen(navController, musicViewModel = musicViewModel)
        }
    }
}