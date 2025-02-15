package com.rure.deepmedi.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rure.deepmedi.presentation.camera.CameraScreen
import com.rure.deepmedi.presentation.home.HomeScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        route = "main/",
        startDestination = Destination.Camera.route
    ) {
        composable(route = Destination.Camera.route) {
            CameraScreen(
                toHome = {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Home.route) { inclusive = true } // Home 화면을 백스택에서 제거
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Destination.Home.route) {
            HomeScreen(
                toCamera = {
                    navController.navigate(Destination.Camera.route) {
                        popUpTo(Destination.Home.route) { inclusive = true } // Home 화면을 백스택에서 제거
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}