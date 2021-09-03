package gsihome.reyst.apod.data

import gsihome.reyst.apod.DATE
import gsihome.reyst.apod.IMAGE_RESULT
import gsihome.reyst.apod.URL
import gsihome.reyst.apod.domain.APODRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DefaultAPODRepositoryTest {

    private lateinit var repository: APODRepository
    private val dataSource: APODRemoteDataSource = mockk()

    @Before
    fun setUp() {
        repository = DefaultAPODRepository(dataSource, Dispatchers.Unconfined)
    }

    @Test
    fun `repository should return flow with an url and call method of the data source once`() = runBlocking {

        coEvery { dataSource.getImageDataByDate(any()) } returns IMAGE_RESULT

        val results = mutableListOf<String>()
        val job = launch {
            repository.getImageUrlByDate(DATE).toList(results)
        }

        coVerify(exactly = 1) { dataSource.getImageDataByDate(eq(DATE)) }

        Assert.assertEquals(URL, results.lastOrNull())

        job.cancel()
    }

}