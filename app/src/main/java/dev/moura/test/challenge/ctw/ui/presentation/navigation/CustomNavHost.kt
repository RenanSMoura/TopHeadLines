package dev.moura.test.challenge.ctw.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.moura.test.challenge.ctw.ui.presentation.navigation.details.detailsScreen
import dev.moura.test.challenge.ctw.ui.presentation.navigation.home.HOME_ROUTE
import dev.moura.test.challenge.ctw.ui.presentation.navigation.home.homeScreen

@Composable
fun CustomNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        homeScreen(navController)

        detailsScreen(navController)
    }
}
