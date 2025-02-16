package com.rure.deepmedi.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rure.deepmedi.presentation.camera.CameraScreen
import com.rure.deepmedi.presentation.home.HomeScreen

private const val LOGIN_ID = "loginId"
private const val PASSWORD = "password"

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        route = "main/",
        startDestination = Destination.Camera.route
    ) {
        composable(route = Destination.Camera.route) {
            CameraScreen(
                toHome = { loginId, password ->
                    navController.navigate(Destination.Home.route + "/$loginId" + "/$password") {
                        popUpTo(Destination.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Destination.Home.route + "/{$LOGIN_ID}" + "/{$PASSWORD}") {
            val id = it.arguments?.getString(LOGIN_ID) ?: throw  Exception("No Arguments For id.")
            val pwd = it.arguments?.getString(PASSWORD) ?: throw  Exception("No Arguments For password.")

            HomeScreen(
                loginId = id,
                password = pwd,
                toCamera = {
                    navController.navigate(Destination.Camera.route) {
                        popUpTo(Destination.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}