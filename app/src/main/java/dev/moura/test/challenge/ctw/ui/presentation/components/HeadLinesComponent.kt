package dev.moura.test.challenge.ctw.ui.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import dev.moura.test.challenge.ctw.domain.model.HeadLine
import dev.moura.test.challenge.ctw.ui.presentation.HeadLinesUiState

@Composable
fun HeadLinesComponent(
    uiState: HeadLinesUiState,
    onItemClick: (HeadLine) -> Unit,
    loadNextHeadLines: () -> Unit
) {
    LazyColumn {
        items(uiState.headLines.size, key = { it }) { i ->
            val item = uiState.headLines[i]
            if (i >= uiState.headLines.size - 1 &&
                !uiState.endReach &&
                uiState.uiStateLoading != HeadLinesUiState.UiStateLoading.Loading
            ) {
                loadNextHeadLines()
            }
            HeadlineItem(headline = item) {
                onItemClick(item)
            }
        }
    }
}
