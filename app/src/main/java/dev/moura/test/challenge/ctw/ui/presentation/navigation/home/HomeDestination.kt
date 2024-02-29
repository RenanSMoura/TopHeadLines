package dev.moura.test.challenge.ctw.ui.presentation.navigation.home

import dev.moura.test.challenge.ctw.ui.presentation.navigation.details.HeadLineDetails

data class HomeDestination(
    val navigateToDetails: (HeadLineDetails) -> Unit
)
