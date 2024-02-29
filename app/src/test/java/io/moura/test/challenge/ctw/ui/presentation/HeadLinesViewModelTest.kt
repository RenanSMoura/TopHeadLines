package io.moura.test.challenge.ctw.ui.presentation

import app.cash.turbine.test
import io.moura.test.challenge.ctw.CoroutineTestRule
import io.moura.test.challenge.ctw.domain.exceptions.BaseExceptions
import io.moura.test.challenge.ctw.domain.model.DomainData
import io.moura.test.challenge.ctw.domain.repository.FakeGetHeadLinesRepository
import io.moura.test.challenge.ctw.factory.generateRandomHeadLines
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HeadLinesViewModelTest {

    private lateinit var sut: HeadLinesViewModel

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: FakeGetHeadLinesRepository

    @get:Rule
    val rule = CoroutineTestRule(testDispatcher)

    @Before
    fun setUp() {
        repository = FakeGetHeadLinesRepository()
        sut = HeadLinesViewModel(
            testDispatcher,
            repository
        )
    }

    @Test
    fun `before emit first item, check if uiState is loading`() = runTest {
        sut.uiState.test {
            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Loading
                )
            )
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when emit items, check if has a list of HeadLines`() = runTest {
        val headLines = generateRandomHeadLines(5)
        val nextPage = 1
        sut.uiState.test {
            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Loading
                )
            )
            repository.emit(DomainData(headLines))
            repository.getHeadLines(nextPage)

            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Idle,
                    headLines = headLines,
                    page = nextPage
                )
            )
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `When emit second page event, check if the headLine list is correct`() = runTest {
        val headLines = generateRandomHeadLines(5)
        val nextPage = 1
        sut.uiState.test {
            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Loading
                )
            )
            repository.emit(DomainData(headLines))
            repository.getHeadLines(nextPage)

            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Idle,
                    headLines = headLines,
                    page = nextPage
                )
            )

            val newHeadLines = generateRandomHeadLines(5)
            repository.emit(DomainData(newHeadLines))
            repository.getHeadLines(nextPage + 1)

            val thirdEvent = awaitItem()

            assertEquals(
                thirdEvent,
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Idle,
                    headLines = headLines + newHeadLines,
                    page = nextPage + 1
                )
            )
            assertEquals(
                thirdEvent.headLines.size,
                (headLines + newHeadLines).size
            )

            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when no new data has been added to uiState, endReach should be true`() = runTest {
        val headLines = generateRandomHeadLines(5)
        val nextPage = 1
        sut.uiState.test {
            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Loading
                )
            )
            repository.emit(DomainData(headLines))
            repository.getHeadLines(nextPage)

            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Idle,
                    headLines = headLines,
                    page = nextPage
                )
            )
            repository.emit(DomainData(emptyList()))
            repository.getHeadLines(nextPage + 1)

            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Idle,
                    headLines = headLines,
                    page = nextPage + 1,
                    endReach = true
                )
            )

            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when error happens, uiState should have a errorMessage`() = runTest {
        val nextPage = 1
        sut.uiState.test {
            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Loading
                )
            )
            repository.emit(DomainData(data = emptyList(), exception = BaseExceptions.Unauthorized))
            repository.getHeadLines(nextPage)

            assertEquals(
                awaitItem(),
                HeadLinesUiState().copy(
                    uiStateLoading = HeadLinesUiState.UiStateLoading.Error,
                    headLines = emptyList(),
                    errorMessage = BaseExceptions.Unauthorized.message
                )
            )
            ensureAllEventsConsumed()
        }
    }
}
