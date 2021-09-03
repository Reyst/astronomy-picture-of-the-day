package gsihome.reyst.apod.data

class APODRetrofitDataSource(
    private val api: APODApi,
    private val key: String,
) : APODRemoteDataSource {
    override fun getImageDataByDate(dateString: String): PictureDto {
        val call = api.getPictureOfDate(dateString, key)
        val response = call.execute()
        return response.body() ?: throw NullPointerException()
    }
}