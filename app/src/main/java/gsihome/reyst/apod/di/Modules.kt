package gsihome.reyst.apod.di

import android.annotation.SuppressLint
import com.google.gson.Gson
import gsihome.reyst.apod.R
import gsihome.reyst.apod.data.APODApi
import gsihome.reyst.apod.data.APODRemoteDataSource
import gsihome.reyst.apod.data.APODRetrofitDataSource
import gsihome.reyst.apod.data.DefaultAPODRepository
import gsihome.reyst.apod.domain.APODRepository
import gsihome.reyst.apod.network.HttpClientFactory
import gsihome.reyst.apod.network.RetrofitManager
import gsihome.reyst.apod.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
val mainModule = module {

    single {
        RetrofitManager(
            androidContext().getString(R.string.base_url),
            HttpClientFactory.createHttpClient(),
            Gson(),
        )
    }

    single { get<RetrofitManager>().getService(APODApi::class.java) }

    single<APODRemoteDataSource> {
        APODRetrofitDataSource(
            get(),
            androidContext().getString(R.string.key),
        )
    }
    single<APODRepository> { DefaultAPODRepository(get()) }

    viewModel { MainViewModel(SimpleDateFormat("yyyy-MM-dd"), get()) }
}
