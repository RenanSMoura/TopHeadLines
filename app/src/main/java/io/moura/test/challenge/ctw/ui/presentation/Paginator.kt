package io.moura.test.challenge.ctw.ui.presentation

interface Paginator<Key, Item> {
    suspend fun loadNextItem()
    fun reset()
}
