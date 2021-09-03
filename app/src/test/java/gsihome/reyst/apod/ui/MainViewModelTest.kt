package gsihome.reyst.apod.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gsihome.reyst.apod.DATE
import gsihome.reyst.apod.URL
import gsihome.reyst.apod.domain.APODRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var vm: MainViewModel

    private val sdf: SimpleDateFormat = mockk()
    private val repository: APODRepository = mockk()


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined) //
        vm = MainViewModel(sdf, repository)
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun `should be emitted values for data field and image url`() = runBlocking {
        coEvery { repository.getImageUrlByDate(any()) } returns flowOf(URL)
        coEvery { sdf.format(any()) } returns DATE


        val urlResults = mutableListOf<String>()
        val dateResults = mutableListOf<Pair<Long, String>>()

        vm.setDate(Calendar.getInstance())

        val job1 = launch {
            vm.urlStateFlow.toList(urlResults)
        }

        val job2 = launch {
            vm.dataPresentation.toList(dateResults)
        }

        coVerify(exactly = 1) { sdf.format(any()) }
        coVerify(exactly = 1) { repository.getImageUrlByDate(eq(DATE)) }

        Assert.assertEquals(URL, urlResults.lastOrNull())
        Assert.assertEquals(DATE, dateResults.lastOrNull()?.second)

        job1.cancel()
        job2.cancel()
    }

}