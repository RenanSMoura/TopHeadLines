package io.moura.test.challenge.ctw.ui.presentation

import io.moura.test.challenge.ctw.CoroutineTestRule
import io.moura.test.challenge.ctw.domain.repository.FakeGetHeadLinesRepository
import io.moura.test.challenge.ctw.domain.repository.GetHeadLinesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HeadLinesViewModelTest {

    private lateinit var sut: HeadLinesViewModel

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: GetHeadLinesRepository

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
}
