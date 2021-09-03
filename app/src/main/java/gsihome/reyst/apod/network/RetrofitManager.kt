package gsihome.reyst.apod.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager(baseUrl: String, httpClient: OkHttpClient, gson: Gson) {

    private val retrofit = retrofitBuilder(baseUrl, httpClient, gson).build()

    private fun retrofitBuilder(baseUrl: String, httpClient: OkHttpClient, gson: Gson) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)

    fun <T> getService(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}

