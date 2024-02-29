package dev.moura.test.challenge.ctw.ui.presentation.navigation.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.moura.test.challenge.ctw.ui.presentation.screens.home.DetailsRoute

const val DETAILS_ROUTE = "details_route"
const val DETAILS_KEY = "headline_key"

fun NavGraphBuilder.detailsScreen(navHost: NavHostController) {
    composable(DETAILS_ROUTE) {
        DetailsRoute(navHost)
    }
}

fun NavHostController.navigateToDetailsScreen(headLineDetails: HeadLineDetails) {
    currentBackStackEntry?.savedStateHandle?.set(DETAILS_KEY, headLineDetails)
    this.navigate(DETAILS_ROUTE)
}
