package gsihome.reyst.apod.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object HttpClientFactory {

    fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLogger())
            .build()
    }

    private fun getLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}