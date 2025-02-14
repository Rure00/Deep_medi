package com.rure.deepmedi.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rure.deepmedi.presentation.camera.CameraScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController, onScreenChanged: (Destination) -> Unit) {
    navigation(
        route = "main/",
        startDestination = Destination.Camera.route
    ) {
        composable(route = Destination.Camera.route) {
            CameraScreen()
        }
        composable(route = Destination.Home.route) {

        }
    }
}