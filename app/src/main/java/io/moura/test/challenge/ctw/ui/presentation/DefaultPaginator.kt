package io.moura.test.challenge.ctw.ui.presentation

import io.moura.test.challenge.ctw.domain.model.DomainData
import kotlinx.coroutines.flow.Flow

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Flow<DomainData<List<Item>>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItem() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        result.collect {
            if (it.exception != null) {
                onError(it.exception)
                onLoadUpdated(false)
            } else {
                currentKey = getNextKey(it.data ?: emptyList())
                onSuccess(it.data ?: emptyList(), currentKey)
                onLoadUpdated(false)
            }
        }
    }

    override fun reset() {
        currentKey = initialKey
    }

}
