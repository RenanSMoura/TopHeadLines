package dev.moura.test.challenge.ctw.ui.presentation

sealed interface HeadLineEvents {
    data object LoadNextItems : HeadLineEvents
}
