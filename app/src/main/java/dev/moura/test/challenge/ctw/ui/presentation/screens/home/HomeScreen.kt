package dev.moura.test.challenge.ctw.ui.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.moura.test.challenge.ctw.domain.model.HeadLine
import dev.moura.test.challenge.ctw.ui.presentation.HeadLineEvents
import dev.moura.test.challenge.ctw.ui.presentation.HeadLinesUiState
import dev.moura.test.challenge.ctw.ui.presentation.HeadLinesViewModel
import dev.moura.test.challenge.ctw.ui.presentation.components.ErrorSnackBar
import dev.moura.test.challenge.ctw.ui.presentation.components.HeadLinesComponent
import dev.moura.test.challenge.ctw.ui.presentation.components.LoadingComponent
import dev.moura.test.challenge.ctw.ui.presentation.navigation.details.HeadLineDetails
import dev.moura.test.challenge.ctw.ui.presentation.navigation.details.navigateToDetailsScreen

@Composable
fun HomeRoute(
    navHost: NavHostController,
    viewModel: HeadLinesViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(navHost = navHost, uiState.value) {
        viewModel.onEvent(HeadLineEvents.LoadNextItems)
    }
}

@Composable
fun HomeScreen(
    navHost: NavHostController,
    uiState: HeadLinesUiState,
    loadNextHeadLines: () -> Unit
) {

    Surface(color = MaterialTheme.colorScheme.onBackground) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState.uiStateLoading) {
                HeadLinesUiState.UiStateLoading.Error -> {
                    OnError(
                        message = uiState.errorMessage,
                        isListEmpty = uiState.headLines.isEmpty()
                    )
                }

                HeadLinesUiState.UiStateLoading.Idle -> {
                    ShowHeadlines(
                        uiState = uiState,
                        loadNextHeadLines = loadNextHeadLines
                    ) {
                        navHost.navigateToDetailsScreen(
                            HeadLineDetails(
                                title = it.title,
                                imageUrl = it.urlToImage,
                                content = it.content
                            )
                        )
                    }
                }

                HeadLinesUiState.UiStateLoading.Loading -> {
                    ShowLoading(
                        uiStateLoading = uiState.uiStateLoading,
                        isListEmpty = uiState.headLines.isEmpty()
                    )
                }
            }
        }
    }
}

@Composable
internal fun OnError(message: String?, isListEmpty: Boolean) {
    val snackBarHostState = remember { SnackbarHostState() }
    if (isListEmpty) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = message ?: "")
        }
    } else {
        ErrorSnackBar(snackBarHostState, message)
    }
}

@Composable
internal fun ShowHeadlines(
    uiState: HeadLinesUiState,
    loadNextHeadLines: () -> Unit,
    onHeadLineClick: (HeadLine) -> Unit
) {
    HeadLinesComponent(
        uiState = uiState,
        onItemClick = {
            onHeadLineClick(it)
        },
        loadNextHeadLines = {
            loadNextHeadLines()
        }
    )
}

@Composable
internal fun ShowLoading(uiStateLoading: HeadLinesUiState.UiStateLoading, isListEmpty: Boolean) {
    if (isListEmpty && uiStateLoading is HeadLinesUiState.UiStateLoading.Loading) {
        LoadingComponent()
    }
}
