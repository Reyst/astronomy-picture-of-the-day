package gsihome.reyst.apod.data

interface APODRemoteDataSource {

    fun getImageDataByDate(dateString: String): PictureDto

}