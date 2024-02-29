package io.moura.test.challenge.ctw.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.moura.test.challenge.ctw.domain.di.IoDispatcher
import io.moura.test.challenge.ctw.domain.model.HeadLine
import io.moura.test.challenge.ctw.domain.repository.GetHeadLinesRepository
import io.moura.test.challenge.ctw.ui.presentation.HeadLinesUiState.UiStateLoading
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HeadLinesUiState(
    val headLines: List<HeadLine> = mutableListOf(),
    val uiStateLoading: UiStateLoading = UiStateLoading.Idle,
    val errorMessage: String? = null,
    val page: Int = 0,
    val endReach: Boolean = false,
    val totalPages: Int = 0
) {

    sealed interface UiStateLoading {
        data object Loading : UiStateLoading
        data object Idle : UiStateLoading
        data object Error : UiStateLoading
    }
}

@HiltViewModel
class HeadLinesViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val repository: GetHeadLinesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HeadLinesUiState())
    val uiState: StateFlow<HeadLinesUiState> = _uiState.asStateFlow()

    private val paginator = DefaultPaginator(
        initialKey = _uiState.value.page,
        onLoadUpdated = { loadState ->
            _uiState.update {
                it.copy(
                    uiStateLoading = loadState
                )
            }
        },
        getNextKey = {
            _uiState.value.page + 1
        },
        onError = {
            handleWithException(it)
        },
        onSuccess = { item, newKey ->
            updateHeadLines(item, newKey)
        },
        onRequest = { nextPage ->
            repository.getHeadLines(nextPage)
        }
    )

    init {
        loadHeadLines()
    }

    private fun loadHeadLines() = viewModelScope.launch(dispatcher) {
        paginator.loadNextItem()
    }

    private fun updateHeadLines(headLines: List<HeadLine>, newKey: Int) {
        _uiState.update {
            it.copy(
                headLines = _uiState.value.headLines + headLines,
                uiStateLoading = UiStateLoading.Idle,
                errorMessage = null,
                endReach = headLines.isEmpty(),
                page = newKey
            )
        }
    }

    private fun handleWithException(exception: Throwable?) {
        _uiState.update {
            it.copy(
                uiStateLoading = UiStateLoading.Error,
                errorMessage = exception?.message
            )
        }
    }
}
