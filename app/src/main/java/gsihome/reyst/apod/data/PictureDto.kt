package gsihome.reyst.apod.data

import com.google.gson.annotations.SerializedName

data class PictureDto(
    @SerializedName("copyright") val copyright : String?,
    @SerializedName("date") val date : String?,
    @SerializedName("explanation") val explanation : String?,
    @SerializedName("hdurl") val hdurl : String?,
    @SerializedName("media_type") val mediaType : String?,
    @SerializedName("service_version") val serviceVersion : String?,
    @SerializedName("title") val title : String?,
    @SerializedName("url") val url : String?,
    @SerializedName("thumbnail_url") val thumbUrl: String?,
) {
    val imageUrl: String
        get() = (if (mediaType == "video") thumbUrl else url).orEmpty()
}
