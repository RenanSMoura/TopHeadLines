package dev.moura.test.challenge.ctw.ui.presentation.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.moura.test.challenge.ctw.ui.presentation.screens.home.HomeRoute

const val HOME_ROUTE = "home_route"

fun NavGraphBuilder.homeScreen(navHost: NavHostController) {
    composable(HOME_ROUTE) {
        HomeRoute(navHost)
    }
}
