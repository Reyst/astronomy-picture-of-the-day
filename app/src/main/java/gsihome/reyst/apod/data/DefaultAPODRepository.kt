package gsihome.reyst.apod.data

import gsihome.reyst.apod.domain.APODRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultAPODRepository(
    private val dataSource: APODRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : APODRepository {
    override fun getImageUrlByDate(dateString: String): Flow<String> = flow {
        dataSource
            .getImageDataByDate(dateString)
            .also { emit(it.imageUrl) }
    }.flowOn(ioDispatcher)
}

