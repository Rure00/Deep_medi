package com.rure.deepmedi.presentation.navigation

sealed class Destination(
    val route: String
) {
    data object Camera: Destination(
        "camera"
    )

    data object Home: Destination(
        "home"
    )
}