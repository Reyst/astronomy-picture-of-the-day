package gsihome.reyst.apod.domain

import kotlinx.coroutines.flow.Flow

interface APODRepository {
    fun getImageUrlByDate(dateString: String): Flow<String>
}