package gsihome.reyst.apod.data

import gsihome.reyst.apod.DATE
import gsihome.reyst.apod.IMAGE_RESULT
import gsihome.reyst.apod.URL
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class APODRetrofitDataSourceTest {

    companion object {
        private const val API_KEY = "api_key"
    }

    private lateinit var ds: APODRemoteDataSource
    private val api: APODApi = mockk()
    private val call: Call<PictureDto> = mockk()

    @Before
    fun setUp() {
        ds = APODRetrofitDataSource(api, API_KEY)
    }

    @Test
    fun `data source should return PictureDto and call a method of the api once`() {

        every { api.getPictureOfDate(any(), any()) } returns call
        every { call.execute() } returns Response.success(IMAGE_RESULT)

        val result = ds.getImageDataByDate(DATE)

        verify(exactly = 1) { api.getPictureOfDate(eq(DATE), eq(API_KEY)) }

        assertEquals(URL, result.imageUrl)
    }

}