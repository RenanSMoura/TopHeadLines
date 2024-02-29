package dev.moura.test.challenge.ctw.ui.presentation

interface Paginator<Key, Item> {
    suspend fun loadNextItem()
}
