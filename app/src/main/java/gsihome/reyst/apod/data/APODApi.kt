package gsihome.reyst.apod.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APODApi {
    @GET("/planetary/apod?thumbs=true")
    fun getPictureOfDate(
        @Query("date") date: String,
        @Query("api_key") key: String,
    ): Call<PictureDto>
}