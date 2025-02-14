package com.rure.deepmedi.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rure.deepmedi.presentation.camera.CameraScreen
import com.rure.deepmedi.presentation.home.HomeScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController, onScreenChanged: (Destination) -> Unit) {
    navigation(
        route = "main/",
        startDestination = Destination.Camera.route
    ) {
        composable(route = Destination.Camera.route) {
            CameraScreen(
                toHome = {
                    navController.navigate(Destination.Home.route)
                }
            )
        }
        composable(route = Destination.Home.route) {
            HomeScreen(
                toCamera = {
                    navController.navigate(Destination.Camera.route)
                }
            )
        }
    }
}